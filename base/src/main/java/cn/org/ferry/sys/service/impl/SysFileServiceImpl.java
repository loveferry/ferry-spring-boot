package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.dto.BaseDTO;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.core.utils.ConfigUtil;
import cn.org.ferry.mybatis.enums.IfOrNotFlag;
import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.exceptions.AttachmentException;
import cn.org.ferry.sys.exceptions.FileException;
import cn.org.ferry.sys.mapper.SysFileMapper;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.sys.service.SysAttachmentService;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.sys.utils.ExcelConfig;
import cn.org.ferry.sys.utils.FileUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFile> implements SysFileService {
    private static final Logger logger = LoggerFactory.getLogger(SysFileServiceImpl.class);

    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private SysAttachmentService sysAttachmentService;
    @Autowired
    private SysAttachmentCategoryService sysAttachmentCategoryService;

    @Override
    public void download(HttpServletResponse httpServletResponse, Long fileId) throws FileException {
        SysFile sysFile = this.selectByPrimaryKey(fileId);
        if(null == sysFile){
            throw new FileException("未找到文件");
        }
        File file = new File(sysFile.getFilePath());
        try (FileInputStream fis = new FileInputStream(file);
             ServletOutputStream sos = httpServletResponse.getOutputStream()
        ){
            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(sysFile.getFileName(), "UTF-8") + "\"");
            httpServletResponse.setContentType(sysFile.getFileType()+";charset=" + "UTF-8");
            httpServletResponse.setHeader("Accept-Ranges", "bytes");
            int fileLength = (int)file.length();
            httpServletResponse.setContentLength(fileLength);
            if(fileLength>0){
                byte[] bs = new byte[1024];
                int length;
                while(-1 != (length = fis.read(bs))){
                    sos.write(bs, 0, length);
                }
                sos.flush();
            }
        } catch (IOException e) {
            FileException fileException = new FileException("文件下载错误");
            fileException.initCause(e);
            throw fileException;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertFileAndAttachment(String sourceType, String sourceKey, String filePath, String fileName, String contentType) {
        File file = new File(filePath);
        if(!file.exists()){
            throw new AttachmentException("文件不存在："+filePath);
        }
        if(file.isDirectory()){
            throw new AttachmentException("不是一个文件："+filePath);
        }
        SysAttachment sysAttachment = sysAttachmentService.queryBySourceTypeAndSourceKey(sourceType, sourceKey);
        SysFile sysFile = new SysFile();
        sysFile.setFileType(contentType);
        sysFile.setFilePath(filePath);
        sysFile.setFileName(fileName);
        sysFile.setFileSize(file.length());

        if(null == sysAttachment){
            sysAttachment = new SysAttachment();
            sysAttachment.setSourceKey(sourceKey);
            sysAttachment.setSourceType(sourceType);
            sysAttachmentService.insertSelective(sysAttachment);
            sysFile.setAttachmentId(sysAttachment.getAttachmentId());
            insertSelective(sysFile);
        }else{
            sysFile.setAttachmentId(sysAttachment.getAttachmentId());
            SysAttachmentCategory sysAttachmentCategory = sysAttachmentCategoryService.queryBySourceType(sourceType);
            if(sysAttachmentCategory.getUniqueFlag() == IfOrNotFlag.N){
                insertSelective(sysFile);
            }else{
                deleteByAttachmentId(sysAttachment.getAttachmentId());
                insertSelective(sysFile);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByAttachmentId(Long attachmentId) {
        if(null == attachmentId){
            throw new AttachmentException("附件id为空");
        }
        logger.info("开始删除文件...");
        List<SysFile> sysFileList = sysFileMapper.queryByAttachmentId(attachmentId);
        if(CollectionUtils.isEmpty(sysFileList)){
            return ;
        }
        // 物理删除
        for (SysFile sysFile : sysFileList) {
            File file = new File(sysFile.getFilePath());
            if(file.exists()){
                file.delete();
            }
        }
        // 逻辑删除
        int count = sysFileMapper.deleteFileByAttachmentId(attachmentId);
        logger.info("删除文件{}份", count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFileByPrimaryKey(Long fileId) {
        logger.info("开始删除文件...");
        SysFile sysFile = sysFileMapper.selectByPrimaryKey(fileId);
        File file = new File(sysFile.getFilePath());
        if(file.exists()){
            file.delete();
        }
        int count = sysFileMapper.deleteByPrimaryKey(fileId);
        logger.info("删除文件{}份", count);
    }

    @Override
    public List<SysFile> queryByAttachmentId(Long attachmentId) {
        Objects.requireNonNull(attachmentId, "附件id为空");
        return sysFileMapper.queryByAttachmentId(attachmentId);
    }

    @Override
    public List<SysFile> queryBySourceTypeAndSourceKey(String sourceType, String sourceKey) {
        if(StringUtils.isEmpty(sourceType)){
            throw new AttachmentException("附件类型为空");
        }
        if(StringUtils.isEmpty(sourceKey)){
            throw new AttachmentException("附件编码为空");
        }
        sysAttachmentCategoryService.validata(sourceType);
        return sysFileMapper.queryBySourceTypeAndSourceKey(sourceType, sourceKey);
    }

    @Override
    public void excelExport(HttpServletResponse response, List<? extends BaseDTO> list, String config) {
        ExcelConfig excelConfig = new ExcelConfig();
        JSONObject jsonObject = JSON.parseObject(config);
        excelConfig.setHeader(jsonObject.getString(ExcelConfig.FIELD_HEADER));
        excelConfig.setFileName(jsonObject.getString(ExcelConfig.FIELD_FILENAME));
        excelConfig.setSheetSize(jsonObject.getIntValue(ExcelConfig.FIELD_SHEETSIZE));
        excelConfig.setSheetName(jsonObject.getString(ExcelConfig.FIELD_SHEETNAME));
        excelConfig.setColumns(jsonObject.getJSONArray(ExcelConfig.FIELD_COLUMNS));
        FileUtils.exportExcelForXLSXByBaseDTO(response, list, excelConfig);
    }

    @Override
    public void excelExport(HttpServletResponse response, Map<String, List<? extends BaseDTO>> map, String config) {
        SXSSFWorkbook sheets = new SXSSFWorkbook(FileUtils.ROW_ACCESS_WINDOW_SIZE);
        ExcelConfig excelConfig = new ExcelConfig();
        JSONObject jsonObject = JSON.parseObject(config);
        excelConfig.setHeader(jsonObject.getString(ExcelConfig.FIELD_HEADER));
        excelConfig.setFileName(jsonObject.getString(ExcelConfig.FIELD_FILENAME));
        excelConfig.setSheetSize(jsonObject.getIntValue(ExcelConfig.FIELD_SHEETSIZE));
        excelConfig.setColumns(jsonObject.getJSONArray(ExcelConfig.FIELD_COLUMNS));
        excelConfig.init(sheets);
        for(Map.Entry<String, List<? extends BaseDTO>> entry: map.entrySet()){
            excelConfig.setSheetName(entry.getKey());
            FileUtils.exportExcelSheetByBaseDTO(entry.getValue(), excelConfig, sheets);
        }
        FileUtils.outputResponse(response, sheets, excelConfig.getFileName());
    }
}
