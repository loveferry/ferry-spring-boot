package cn.org.ferry.sys.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.dto.model.SysAttachmentUploadOrDownload;
import cn.org.ferry.sys.service.SysAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "附件控制器", position = 900)
@Controller
@RequestMapping("/api/sys/attachment")
public class SysAttachmentController {
    @Autowired
    private SysAttachmentService sysAttachmentService;
    /**
     * 文件上传,可以上传多个文件
     */
    @ApiOperation(value = "附件上传", position = 910)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @LoginPass
    @ResponseBody
    public ResponseData upload(HttpServletRequest request, SysAttachmentUploadOrDownload sysAttachment) {
        sysAttachmentService.upload(request, sysAttachment);
        return new ResponseData();
    }

    /**
     * 附件下载
     */
    @ApiOperation(value = "附件下载", position = 920)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "附件类型"),
            @ApiImplicitParam(name = "sourceKey", value = "附件编码")
    })
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @LoginPass
    public void download(HttpServletResponse response, String sourceType, String sourceKey) {
        sysAttachmentService.download(response, sourceType, sourceKey);
    }
}
