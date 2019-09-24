package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.exceptions.AttachmentException;
import cn.org.ferry.sys.mapper.SysAttachmentMapper;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.sys.service.SysAttachmentService;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import cn.org.ferry.system.sysenum.EnableFlag;
import cn.org.ferry.system.sysenum.IfOrNotFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

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
