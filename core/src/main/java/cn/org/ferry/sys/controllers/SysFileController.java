package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.system.dto.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class SysFileController {
    @Autowired
    private SysFileService iSysFileService;

    /**
     * 文件上传,可以上传多个文件
     * @param file 文件集合
     * @param sourceType 附件类型
     * @param sourceKey 附件编码
     * @return 返回上传结果
     */
    @RequestMapping("/api/sys/file/upload")
    @ResponseBody
    public ResponseData upload(List<MultipartFile> file, String sourceType, String sourceKey){
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        if(CollectionUtils.isEmpty(file)){
            responseData.setMessage("文件信息为空,请同志检查代码!");
        }else if(StringUtils.isEmpty(sourceKey)){
            responseData.setMessage("附件编码不能为空!");
        }else if(StringUtils.isEmpty(sourceType)){
            responseData.setMessage("附件类型不能为空!");
        }else{
            responseData.setSuccess(iSysFileService.upload(file, sourceKey, sourceType));
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
}
