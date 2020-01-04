package cn.org.ferry.sys.service;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.SysAttachmentCategory;

import java.util.List;

/**
 * 附件类型的接口层
 */
public interface SysAttachmentCategoryService extends BaseService<SysAttachmentCategory> {
    /**
     * 查询附件类型信息
     * @param sourceType 必传参数，此字段有唯一约束，只能查出一条数据
     */
    SysAttachmentCategory queryBySourceType(String sourceType);

    /**
     * 查询附件类型信息
     * @param sourceType 必传参数，此字段有唯一约束，只能查出一条数据
     */
    void validata(String sourceType);

    /**
     * 更新附件类型定义
     */
    ResponseData save(SysAttachmentCategory sysAttachmentCategory);

    /**
     * 查询附件类型
     */
    List<SysAttachmentCategory> query(SysAttachmentCategory sysAttachmentCategory, int page, int pageSize);


}
