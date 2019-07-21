package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.system.service.BaseService;

/**
 * 附件的接口层
 */
public interface SysAttachmentService extends BaseService<SysAttachment> {
    /**
     * 查询附件信息
     * @param sourceKey 附件编码为必传参数
     * @param sourceType 附件类型为必传参数
     */
    SysAttachment queryBySourceTypeAndSourceKey(String sourceType, String sourceKey);
}
