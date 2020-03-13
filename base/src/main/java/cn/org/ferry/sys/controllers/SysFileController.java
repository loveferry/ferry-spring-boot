package cn.org.ferry.sys.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.exceptions.FileException;
import cn.org.ferry.sys.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "文件控制器")
@RestController
public class SysFileController {
    @Autowired
    private SysFileService iSysFileService;

    /**
     * 文件下载
     * @param response 相应对象
     * @param fileId 文件表主键,必须传递该参数
     */
    @LoginPass
    @ApiOperation("文件下载")
    @RequestMapping(value = "/api/sys/file/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, Long fileId) throws FileException {
        iSysFileService.download(response, fileId);
    }

    /**
     * 附件查询
     */
    @ApiOperation("附件查询")
    @RequestMapping(value = "/api/sys/attachment/query", method = RequestMethod.GET)
    public ResponseData queryBySourceTypeAndSourceKey(@RequestParam("sourceKey") String sourceKey, @RequestParam("sourceType") String sourceType){
        return new ResponseData(iSysFileService.queryBySourceTypeAndSourceKey(sourceType, sourceKey));
    }
}
