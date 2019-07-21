package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.mapper.SysAttachmentMapper;
import cn.org.ferry.sys.service.SysAttachmentService;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachment> implements SysAttachmentService {
    @Autowired
    private SysAttachmentMapper iSysAttachmentMapper;

    @Override
    public SysAttachment queryBySourceTypeAndSourceKey(String sourceType, String sourceKey) {
        SysAttachment attachment = new SysAttachment();
        attachment.setSourceType(sourceType);
        attachment.setSourceKey(sourceKey);
        return iSysAttachmentMapper.selectOne(attachment);
    }
}
