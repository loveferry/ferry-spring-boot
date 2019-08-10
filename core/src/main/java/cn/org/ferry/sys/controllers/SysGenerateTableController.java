package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysGenerateTable;
import cn.org.ferry.sys.service.SysGenerateTableService;
import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.sysenum.IfOrNotFlag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class SysGenerateTableController {
    @Autowired
    private SysGenerateTableService sysGenerateTableService;

    /**
     * 代码生成器
     */
    @RequestMapping(value = "/generate/code", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData generate(HttpServletRequest httpServletRequest,@RequestBody SysGenerateTable sysGenerateTable) throws IOException {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        if(null == sysGenerateTable){
            responseData.setMessage("参数为空!");
            return responseData;
        }
        if(StringUtils.isEmpty(sysGenerateTable.getTableName())){
            responseData.setMessage("表名为空!");
            return responseData;
        }
        if(StringUtils.isEmpty(sysGenerateTable.getProjectPath())){
            responseData.setMessage("项目路径不能为空!");
            return responseData;
        }
        File projectPath = new File(sysGenerateTable.getProjectPath());
        if(!projectPath.exists()){
            responseData.setMessage("项目路径不存在!");
            return responseData;
        }
        if(!projectPath.isDirectory()){
            responseData.setMessage("项目路径不是一个路径!");
            return responseData;
        }
        if(StringUtils.isEmpty(sysGenerateTable.getPackagePath())){
            responseData.setMessage("包路径不能为空!");
            return responseData;
        }
        if(IfOrNotFlag.Y == sysGenerateTable.getEntityFlag() && StringUtils.isEmpty(sysGenerateTable.getEntityName())){
            responseData.setMessage("实体类生成标志为\"是\"，但未给定实体类名称!");
            return responseData;
        }
        if(IfOrNotFlag.Y == sysGenerateTable.getMapperJavaFlag() && StringUtils.isEmpty(sysGenerateTable.getMapperJavaName())){
            responseData.setMessage("mybatis接口生成标志为\"是\"，但未给定mybatis接口名称!");
            return responseData;
        }
        if(IfOrNotFlag.Y == sysGenerateTable.getMapperXmlFlag() && StringUtils.isEmpty(sysGenerateTable.getMapperXmlName())){
            responseData.setMessage("mybatis xml文件生成标志为\"是\"，但未给定mybatis xml文件名称!");
            return responseData;
        }
        if(IfOrNotFlag.Y == sysGenerateTable.getServiceFlag() && StringUtils.isEmpty(sysGenerateTable.getServiceName())){
            responseData.setMessage("业务接口生成标志为\"是\"，但未给定业务接口名称!");
            return responseData;
        }
        if(IfOrNotFlag.Y == sysGenerateTable.getServiceImplFlag() && StringUtils.isEmpty(sysGenerateTable.getServiceImplName())){
            responseData.setMessage("业务接口实现类生成标志为\"是\"，但未给定业务接口实现类名称!");
            return responseData;
        }
        if(IfOrNotFlag.Y == sysGenerateTable.getControllerFlag() && StringUtils.isEmpty(sysGenerateTable.getControllerName())){
            responseData.setMessage("控制器生成标志为\"是\"，但未给定控制器名称!");
            return responseData;
        }
        return sysGenerateTableService.generate(sysGenerateTable);
    }
}
