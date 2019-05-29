package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.mapper.SysFileMapper;
import cn.org.ferry.sys.service.SysAttachmentService;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.sys.utils.ExcelConfig;
import cn.org.ferry.sys.utils.FileUtils;
import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import cn.org.ferry.system.utils.BeanUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private static final String TEMP_FILE_PATH = "/u01/upload/test/";
    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private SysAttachmentService sysAttachmentService;

    @Transactional
    @Override
    public boolean upload(List<MultipartFile> files, String sourceKey, String sourceType) {
        SysAttachment attachment = new SysAttachment();
        attachment.setSourceKey(sourceKey);
        attachment.setSourceType(sourceType);
        sysAttachmentService.insertSelective(attachment);
        for(MultipartFile multipartFile : files){
            String name = getRandomString(16);
            SysFile sysFile = new SysFile();
            sysFile.setFileName(multipartFile.getOriginalFilename());
            sysFile.setFileType(multipartFile.getContentType());
            sysFile.setFilePath(TEMP_FILE_PATH+name);
            sysFile.setFileSize(multipartFile.getSize());
            sysFile.setAttachmentId(attachment.getAttachmentId());
            this.insertSelective(sysFile);
            File dir = new File(TEMP_FILE_PATH);
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
