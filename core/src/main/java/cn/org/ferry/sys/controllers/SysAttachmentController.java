package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.service.SysAttachmentService;
import cn.org.ferry.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysAttachmentController {
    @Autowired
    private SysAttachmentService sysAttachmentService;

    /**
     * 附件查询
     * @param sysAttachment 查询条件
     * @return 返回符合条件的附件集合
     */
    @RequestMapping("/api/sys/attachment/query")
    @ResponseBody
    public ResponseData query(SysAttachment sysAttachment){
        return new ResponseData(sysAttachmentService.query(sysAttachment));
    }
}
