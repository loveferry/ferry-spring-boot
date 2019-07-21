package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.mapper.SysFileMapper;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.sys.service.SysAttachmentService;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.sys.utils.ExcelConfig;
import cn.org.ferry.sys.utils.FileUtils;
import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.exception.FileException;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import cn.org.ferry.system.sysenum.EnableFlag;
import cn.org.ferry.system.sysenum.IfOrNotFlag;
import cn.org.ferry.system.utils.BeanUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFile> implements SysFileService {
    private static final String msg = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890!@#$%^&*()<>?:[]{}";

    @Value("${ferry.upload}")
    private String UPLOAD_PATH;

    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private SysAttachmentService sysAttachmentService;
    @Autowired
    private SysAttachmentCategoryService sysAttachmentCategoryService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean upload(List<MultipartFile> files, SysAttachment sysAttachment) throws FileException {
        SysAttachmentCategory sysAttachmentCategory = sysAttachmentCategoryService.query(sysAttachment.getSourceType());
        if(EnableFlag.N == sysAttachmentCategory.getEnableFlag()){
            throw new FileException("该附件类型已被禁用！");
        }
        SysAttachment attachment = sysAttachmentService.queryBySourceTypeAndSourceKey(sysAttachment.getSourceType(), sysAttachment.getSourceKey());
        if(null == attachment){
            sysAttachmentService.insertSelective(sysAttachment);
        }else{
            sysAttachment.setAttachmentId(attachment.getAttachmentId());
            sysAttachmentService.updateByPrimaryKey(sysAttachment);
        }
        if(IfOrNotFlag.Y == sysAttachmentCategory.getUniqueFlag()){
            if(files.size() > 1){
                throw new FileException("该类型的附件只允许上传一份!");
            }
            deleteByAttachmentId(sysAttachment.getAttachmentId());
        }
        for(MultipartFile multipartFile : files){
            String name = getRandomString(16);
            SysFile sysFile = new SysFile();
            sysFile.setFileName(multipartFile.getOriginalFilename());
            sysFile.setFileType(multipartFile.getContentType());
            sysFile.setFilePath(UPLOAD_PATH+name);
            sysFile.setFileSize(multipartFile.getSize());
            sysFile.setAttachmentId(sysAttachment.getAttachmentId());
            this.insertSelective(sysFile);
            File dir = new File(UPLOAD_PATH);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File f = new File(dir.getAbsolutePath()+File.separator+name);
            f = validataFileExists(f);
            try {
                multipartFile.transferTo(f);
            } catch (IOException e) {
                logger.error("文件上传出错", e);
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public void fileDownload(HttpServletResponse httpServletResponse, Long fileId) {
        SysFile sysFile = this.selectByPrimaryKey(fileId);
        if(null == sysFile){
            throw new IllegalArgumentException("未找到文件");
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
                int length = -1;
                while(-1 != (length = fis.read(bs))){
                    sos.write(bs, 0, length);
                }
                sos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SysFile> query(SysFile sysFile) {
        return sysFileMapper.query(sysFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByAttachmentId(Long attachmentId) {
        if(null == attachmentId){
            throw new NullPointerException("missing attachment_id");
        }
        List<SysFile> sysFileList = sysFileMapper.queryByAttachmentId(attachmentId);
        if(CollectionUtils.isEmpty(sysFileList)){
            return ;
        }
        for (SysFile sysFile : sysFileList) {
            File file = new File(sysFile.getFilePath());
            if(file.exists()){
                file.delete();
            }
            sysFileMapper.deleteByPrimaryKey(sysFile.getFileId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFileByPrimaryKey(Long fileId) {
        SysFile sysFile = sysFileMapper.selectByPrimaryKey(fileId);
        File file = new File(sysFile.getFilePath());
        if(file.exists()){
            file.delete();
        }
        sysFileMapper.deleteByPrimaryKey(sysFile.getFileId());
        sysFileMapper.deleteByPrimaryKey(fileId);
    }

    private String getRandomString(int length){
        char[] cs = new char[length];
        Random random = new Random();
        for(int i= 0; i < cs.length; i++){
            cs[i] = msg.charAt(random.nextInt(msg.length()));
        }
        return new String(cs);
    }

    private File validataFileExists(File file){
        if(file.exists()){
            file = new File(file.getPath()+File.separator+getRandomString(16));
            file = validataFileExists(file);
        }
        return file;
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
