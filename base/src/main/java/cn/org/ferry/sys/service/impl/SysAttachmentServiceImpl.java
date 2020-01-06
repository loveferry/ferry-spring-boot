package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.exceptions.AttachmentException;
import cn.org.ferry.sys.mapper.SysAttachmentMapper;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.sys.service.SysAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachment> implements SysAttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(SysAttachmentServiceImpl.class);

    @Autowired
    private SysAttachmentMapper sysAttachmentMapper;
    @Autowired
    private SysAttachmentCategoryService sysAttachmentCategoryService;

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