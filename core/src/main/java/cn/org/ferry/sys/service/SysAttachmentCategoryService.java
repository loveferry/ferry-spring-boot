package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.system.service.BaseService;

/**
 * 附件类型的接口层
 */
public interface SysAttachmentCategoryService extends BaseService<SysAttachmentCategory> {
    /**
     * 查询附件类型信息
     * @param sourceType 必传参数，此字段有唯一约束，只能查出一条数据，不存在返回空
     */
    SysAttachmentCategory query(String sourceType);
}
