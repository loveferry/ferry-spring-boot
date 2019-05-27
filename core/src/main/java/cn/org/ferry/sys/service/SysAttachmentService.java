package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.system.service.BaseService;

import java.util.List;

/**
 * 附件的接口层
 */
public interface SysAttachmentService extends BaseService<SysAttachment> {
    /**
     * 查询附件
     */
    List<SysFile> query(SysAttachment sysAttachment);
}
