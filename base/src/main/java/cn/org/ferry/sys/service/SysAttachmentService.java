package cn.org.ferry.sys.service;

import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.model.SysAttachmentUploadOrDownload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 附件的接口层
 */
public interface SysAttachmentService extends BaseService<SysAttachment> {
    /**
     * 文件上传
     */
    void upload(HttpServletRequest request, SysAttachmentUploadOrDownload sysAttachment);

    /**
     * 附件下载
     */
    void download(HttpServletResponse response, String sourceType, String sourceKey);

    /**
     * 查询附件信息
     * @param sourceKey 附件编码为必传参数
     * @param sourceType 附件类型为必传参数
     */
    SysAttachment queryBySourceTypeAndSourceKey(String sourceType, String sourceKey);
}
