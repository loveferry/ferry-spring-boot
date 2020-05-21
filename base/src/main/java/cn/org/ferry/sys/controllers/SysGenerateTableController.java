package cn.org.ferry.sys.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.mybatis.enums.IfOrNot;
import cn.org.ferry.sys.dto.SysGenerateTable;
import cn.org.ferry.sys.exceptions.FileException;
import cn.org.ferry.sys.service.SysGenerateTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "代码生成器控制器")
@RestController
@RequestMapping("/api")
@LoginPass
public class SysGenerateTableController {
    @Autowired
    private SysGenerateTableService sysGenerateTableService;

    /**
     * 代码生成器
     */
    @ApiOperation(value = "代码生成器", position = 10)
    @RequestMapping(value = "/generate/code", method = RequestMethod.POST)
    public ResponseData generate(HttpServletRequest httpServletRequest,@RequestBody SysGenerateTable sysGenerateTable) throws FileException {
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
        if(IfOrNot.Y == sysGenerateTable.getEntityFlag() && StringUtils.isEmpty(sysGenerateTable.getEntityName())){
            responseData.setMessage("实体类生成标志为\"是\"，但未给定实体类名称!");
            return responseData;
        }
        if(IfOrNot.Y == sysGenerateTable.getMapperJavaFlag() && StringUtils.isEmpty(sysGenerateTable.getMapperJavaName())){
            responseData.setMessage("mybatis接口生成标志为\"是\"，但未给定mybatis接口名称!");
            return responseData;
        }
        if(IfOrNot.Y == sysGenerateTable.getMapperXmlFlag() && StringUtils.isEmpty(sysGenerateTable.getMapperXmlName())){
            responseData.setMessage("mybatis xml文件生成标志为\"是\"，但未给定mybatis xml文件名称!");
            return responseData;
        }
        if(IfOrNot.Y == sysGenerateTable.getServiceFlag() && StringUtils.isEmpty(sysGenerateTable.getServiceName())){
            responseData.setMessage("业务接口生成标志为\"是\"，但未给定业务接口名称!");
            return responseData;
        }
        if(IfOrNot.Y == sysGenerateTable.getServiceImplFlag() && StringUtils.isEmpty(sysGenerateTable.getServiceImplName())){
            responseData.setMessage("业务接口实现类生成标志为\"是\"，但未给定业务接口实现类名称!");
            return responseData;
        }
        if(IfOrNot.Y == sysGenerateTable.getControllerFlag() && StringUtils.isEmpty(sysGenerateTable.getControllerName())){
            responseData.setMessage("控制器生成标志为\"是\"，但未给定控制器名称!");
            return responseData;
        }
        sysGenerateTableService.generate(sysGenerateTable);
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 查询表名
     */
    @ApiOperation(value = "表名查询", position = 10)
    @RequestMapping(value = "/table/name/query", method = RequestMethod.GET)
    public ResponseData queryAllTableName(HttpServletRequest httpServletRequest,
                                          @ApiParam(name = "tableName", value = "表名")
                                          @RequestParam(value = "tableName")String tableName,
                                          @ApiParam(name = "page", value = "当前页")
                                          @RequestParam(value = "page", defaultValue = "1")int page,
                                          @ApiParam(name = "pageSize", value = "页面大小")
                                          @RequestParam(value = "pageSize", defaultValue = "5")int pageSize
                                          ) {
        return new ResponseData(sysGenerateTableService.queryTableNames(tableName, page, pageSize));
    }




}
