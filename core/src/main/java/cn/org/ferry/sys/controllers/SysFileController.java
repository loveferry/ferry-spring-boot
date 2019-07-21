package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.exception.FileException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class SysFileController {
    @Autowired
    private SysFileService iSysFileService;

    /**
     * 文件上传,可以上传多个文件
     */
    @RequestMapping("/api/sys/file/upload")
    @ResponseBody
    public ResponseData upload(HttpServletRequest httpServletRequest, SysAttachment sysAttachment) throws FileException {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        List<MultipartFile> file = ((StandardMultipartHttpServletRequest) httpServletRequest).getFiles("files");
        if(CollectionUtils.isEmpty(file)){
            responseData.setMessage("文件信息为空,请同志检查代码!");
        }else if(StringUtils.isEmpty(sysAttachment.getSourceType())){
            responseData.setMessage("附件编码不能为空!");
        }else if(StringUtils.isEmpty(sysAttachment.getSourceKey())){
            responseData.setMessage("附件类型不能为空!");
        }else{
            responseData.setSuccess(iSysFileService.upload(file, sysAttachment));
        }
        return responseData;
    }

    /**
     * 文件下载
     * @param response 相应对象
     * @param fileId 文件表主键,必须传递该参数
     */
    @RequestMapping("/api/sys/file/download")
    @ResponseBody
    public void download(HttpServletResponse response, Long fileId){
        iSysFileService.fileDownload(response, fileId);
    }

    /**
     * 附件查询
     * @param sysFile 查询条件
     * @return 返回符合条件的附件集合
     */
    @RequestMapping("/api/sys/attachment/query")
    @ResponseBody
    public ResponseData query(SysFile sysFile){
        return new ResponseData(iSysFileService.query(sysFile));
    }
}
