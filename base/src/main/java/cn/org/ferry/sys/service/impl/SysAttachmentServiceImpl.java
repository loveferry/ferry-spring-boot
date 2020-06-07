package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.mybatis.enums.IfOrNot;
import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.dto.model.SysAttachmentUploadOrDownload;
import cn.org.ferry.sys.exceptions.AttachmentException;
import cn.org.ferry.sys.mapper.SysAttachmentMapper;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.sys.service.SysAttachmentService;
import cn.org.ferry.sys.service.SysFileService;
import org.apache.commons.compress.utils.IOUtils;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@PropertySource("classpath:config_base.properties")
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachment> implements SysAttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(SysAttachmentServiceImpl.class);

    @Autowired
    private SysAttachmentMapper sysAttachmentMapper;
    @Autowired
    private SysAttachmentCategoryService sysAttachmentCategoryService;
    @Autowired
    private SysFileService sysFileService;
    @Value("${upload.path}")
    private String upload_path;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void upload(HttpServletRequest request, SysAttachmentUploadOrDownload sysAttachment) {
        logger.info("开始处理附件上传业务...");

        List<MultipartFile> files = ((StandardMultipartHttpServletRequest) request).getFiles("files");
        if(CollectionUtils.isEmpty(files)){
            throw new AttachmentException("上传附件为空!");
        }
        SysAttachmentCategory sysAttachmentCategory = sysAttachmentCategoryService.queryBySourceType(sysAttachment.getSourceType());
        SysAttachment attachment = sysAttachmentMapper.queryBySourceTypeAndSourceKey(sysAttachment.getSourceType(), sysAttachment.getSourceKey());
        if(null == attachment){
            attachment = new SysAttachment();
            attachment.setSourceType(sysAttachment.getSourceType());
            attachment.setSourceKey(sysAttachment.getSourceKey());
            mapper.insertSelective(attachment);
        }else{
            mapper.updateByPrimaryKeySelective(attachment);
        }
        if(IfOrNot.Y == sysAttachmentCategory.getUniqueFlag()){
            if(files.size() > 1){
                throw new AttachmentException("该类型的附件只允许上传一份!");
            }
            sysFileService.deleteByAttachmentId(attachment.getAttachmentId());
        }
        String uploadPath = upload_path+sysAttachmentCategory.getAttachmentPath();
        for(MultipartFile multipartFile : files){
            String name = UUID.randomUUID().toString();
            SysFile sysFile = new SysFile();
            sysFile.setFileName(multipartFile.getOriginalFilename());
            sysFile.setFileType(multipartFile.getContentType());
            sysFile.setFilePath(uploadPath+File.separator+name);
            sysFile.setFileSize(multipartFile.getSize());
            sysFile.setAttachmentId(attachment.getAttachmentId());
            sysFileService.insertSelective(sysFile);
            File dir = new File(uploadPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File f = new File(sysFile.getFilePath());
            try {
                multipartFile.transferTo(f);
            } catch (IOException e) {
                AttachmentException ata = new AttachmentException("文件上传出错");
                ata.initCause(e);
                throw ata;
            }
        }
        logger.info("附件上传成功！");
    }

    @Override
    public void download(HttpServletResponse response, String sourceType, String sourceKey) {
        logger.info("开始处理附件下载业务...");
        List<SysFile> sysFileList = sysFileService.queryBySourceTypeAndSourceKey(sourceType, sourceKey);
        if(CollectionUtils.isEmpty(sysFileList)){
            logger.info("附件列表为空！");
            return ;
        }
        try {
            if(sysFileList.size() == 1){
                logger.info("单个文件直接下载原文件");
                sysFileService.download(response, sysFileList.get(0).getFileId());
            }else{
                logger.info("多个文件压缩成ZIP打包下载");
                // 查找得到附件类型名称
                SysAttachmentCategory category = sysAttachmentCategoryService.queryBySourceType(sourceType);
                if(category == null){
                    throw new AttachmentException("附件类型不存在！");
                }
                // 多个文件压缩成zip下载
                response.reset();
                response.setContentType("application/x-msdownload;");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String((category.getCategoryName()+".zip").getBytes(), StandardCharsets.ISO_8859_1));
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                CheckedOutputStream cos = new CheckedOutputStream(output, new CRC32());
                ZipOutputStream zos = new ZipOutputStream(cos);
                for (SysFile sysFile : sysFileList) {
                    BufferedInputStream bis= new BufferedInputStream(new FileInputStream(sysFile.getFilePath()));
                    ZipEntry entry = new ZipEntry(sysFile.getFileName());
                    zos.putNextEntry(entry);
                    IOUtils.copy(bis,zos);
                    bis.close();
                }
                zos.closeEntry();
                zos.close();

                response.setHeader("Content-Length", String.valueOf(output.size()));
                IOUtils.copy(new ByteArrayInputStream(output.toByteArray()), response.getOutputStream());
                cos.close();
            }
        }catch (Exception e){
            AttachmentException attachmentException = new AttachmentException("附件下载出错了");
            attachmentException.initCause(e);
            throw attachmentException;
        }
        logger.info("附件下载成功...");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteAttachment(Long attachmentId) {
        sysFileService.deleteByAttachmentId(attachmentId);
        return sysAttachmentMapper.deleteByPrimaryKey(-1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteAttachment(String sourceType, String sourceKey) {
        SysAttachment sysAttachment = sysAttachmentMapper.queryBySourceTypeAndSourceKey(sourceType, sourceKey);
        if(null != sysAttachment){
            return deleteAttachment(sysAttachment.getAttachmentId());
        }else{
            return 0;
        }
    }

    @Override
    public SysAttachment queryBySourceTypeAndSourceKey(String sourceType, String sourceKey) {
        if(StringUtils.isEmpty(sourceType)){
            throw new AttachmentException("附件类型不能为空！");
        }
        if(StringUtils.isEmpty(sourceKey)){
            throw new AttachmentException("附件编码不能为空！");
        }
        sysAttachmentCategoryService.validata(sourceType);
        return sysAttachmentMapper.queryBySourceTypeAndSourceKey(sourceType, sourceKey);
    }
}
