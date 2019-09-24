package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.exceptions.AttachmentException;
import cn.org.ferry.sys.mapper.SysAttachmentCategoryMapper;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import cn.org.ferry.system.sysenum.EnableFlag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAttachmentCategoryServiceImpl extends BaseServiceImpl<SysAttachmentCategory> implements SysAttachmentCategoryService {
    @Autowired
    private SysAttachmentCategoryMapper sysAttachmentCategoryMapper;

    @Override
    public SysAttachmentCategory queryBySourceType(String sourceType) {
        if(StringUtils.isEmpty(sourceType)){
            throw new AttachmentException("附件类型为空");
        }
        SysAttachmentCategory sysAttachmentCategory = sysAttachmentCategoryMapper.queryBySourceType(sourceType);
        if(null == sysAttachmentCategory){
            throw new AttachmentException("附件类型不存在");
        }
        if(EnableFlag.N == sysAttachmentCategory.getEnableFlag()){
            throw new AttachmentException("附件类型已禁用");
        }
        return sysAttachmentCategory;
    }

    @Override
    public void validata(String sourceType) {
        if(StringUtils.isEmpty(sourceType)){
            throw new AttachmentException("附件类型为空");
        }
        SysAttachmentCategory sysAttachmentCategory = sysAttachmentCategoryMapper.queryBySourceType(sourceType);
        if(null == sysAttachmentCategory){
            throw new AttachmentException("附件类型不存在");
        }
        if(EnableFlag.N == sysAttachmentCategory.getEnableFlag()){
            throw new AttachmentException("附件类型已禁用");
        }
    }

    @Override
    public ResponseData save(SysAttachmentCategory sysAttachmentCategory) {
        ResponseData responseData = new ResponseData();
        if(StringUtils.isEmpty(sysAttachmentCategory.getSourceType())){
            responseData.setSuccess(false);
            responseData.setMessage("附件类型不能为空");
            return responseData;
        }
        if(null == sysAttachmentCategory.getCategoryId()){
            insertSelective(sysAttachmentCategory);
        }else{
            updateByPrimaryKeySelective(sysAttachmentCategory);
        }
        return responseData;
    }
}
