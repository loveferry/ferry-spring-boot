package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.mapper.SysAttachmentCategoryMapper;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAttachmentCategoryServiceImpl extends BaseServiceImpl<SysAttachmentCategory> implements SysAttachmentCategoryService {
    @Autowired
    private SysAttachmentCategoryMapper sysAttachmentCategoryMapper;

    @Override
    public SysAttachmentCategory query(String sourceType) {
        SysAttachmentCategory sysAttachmentCategory = new SysAttachmentCategory();
        sysAttachmentCategory.setSourceType(sourceType);
        return sysAttachmentCategoryMapper.selectOne(sysAttachmentCategory);
    }
}
