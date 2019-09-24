package cn.org.ferry.doc.service.impl;

import cn.org.ferry.doc.dto.BookMarkType;
import cn.org.ferry.doc.dto.DocTemplate;
import cn.org.ferry.doc.dto.DocTemplateParam;
import cn.org.ferry.doc.exceptions.DocException;
import cn.org.ferry.doc.mapper.DocTemplateMapper;
import cn.org.ferry.doc.service.DocTemplateParamService;
import cn.org.ferry.doc.service.DocTemplateService;
import cn.org.ferry.doc.utils.Docx4jGenerateUtil;
import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.exceptions.AttachmentException;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.sys.service.SysAttachmentService;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.sys.service.SysSqlService;
import cn.org.ferry.system.exception.FileException;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import cn.org.ferry.system.utils.PropertiesUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description
 */
@Service
public class DocTemplateServiceImpl extends BaseServiceImpl<DocTemplate> implements DocTemplateService {
    protected static final Logger logger = LoggerFactory.getLogger(DocTemplateServiceImpl.class);

    @Autowired
    private DocTemplateMapper docTemplateMapper;
    @Autowired
    private DocTemplateParamService docTemplateParamService;
    @Autowired
    private SysSqlService sysSqlService;
    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private SysAttachmentCategoryService sysAttachmentCategoryService;

    @Override
    public DocTemplate queryByTemplateCode(String templateCode) {
        return docTemplateMapper.queryByTemplateCode(templateCode);
    }

    @Override
    public void generateWord(String templateCode, String sourceType, String sourceKey, Map<String, Object> params) {
        if(StringUtils.isEmpty(templateCode)){
            throw new DocException("模版代码为空");
        }
        if(StringUtils.isEmpty(sourceType)){
            throw new AttachmentException("目标附件代码为空");
        }
        // 模版数据
        DocTemplate docTemplate = docTemplateMapper.queryByTemplateCode(templateCode);
        // 获取模版文件
        List<SysFile> sysFileList = sysFileService.queryBySourceTypeAndSourceKey(DOC_TEMPLATE_ATTACHMENT_CATEGORY, docTemplate.getTemplateCode());
        if(CollectionUtils.isEmpty(sysFileList)){
            throw new DocException("模版文档不存在");
        }
        SysFile templateSysFile = sysFileList.get(0);
        // 获取模版参数
        List<DocTemplateParam> docTemplateParamList = docTemplateParamService.queryByTemplateCode(templateCode);
        // 目标附件
        SysAttachmentCategory sysAttachmentCategory = sysAttachmentCategoryService.queryBySourceType(sourceType);
        String targetPath = PropertiesUtils.getProperty("ferry.upload")+File.separator+sysAttachmentCategory.getAttachmentPath()+File.separator+ UUID.randomUUID().toString();
        // 书签替换
        Map<String, Object> bookMarkParams = new HashMap<>(docTemplateParamList.size());
        Map<String, BookMarkType> bookMarkTypes = new HashMap<>(docTemplateParamList.size());
        try {
            for (DocTemplateParam docTemplateParam : docTemplateParamList) {
                String sql = sysSqlService.querySqlBySqlCode(docTemplateParam.getSqlCode());
                bookMarkParams.put(docTemplateParam.getBookMark(), sysSqlService.execute(sql, params));
                bookMarkTypes.put(docTemplateParam.getBookMark(), docTemplateParam.getParamType());
            }
        }catch (SQLException e){
            DocException docException = new DocException("模版参数查询错误");
            docException.initCause(e);
            throw docException;
        }
        try {
            Docx4jGenerateUtil.generateDocxWithReplaceBookMark(bookMarkParams, bookMarkTypes, templateSysFile.getFilePath(), targetPath, true);
            sysFileService.insertFileAndAttachment(sourceType, sourceKey, targetPath, docTemplate.getTemplateName(),
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } catch (FileException e) {
            DocException docException = new DocException(e.getMessage());
            docException.initCause(e);
            throw docException;
        }
    }

}
