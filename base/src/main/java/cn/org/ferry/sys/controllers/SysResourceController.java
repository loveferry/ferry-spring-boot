package cn.org.ferry.sys.controllers;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.dto.SysResource;
import cn.org.ferry.sys.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Generate by code generator
 * 系统资源表 控制器
 */

@Api(tags = "系统资源表控制器", position = 100)
@RestController
@RequestMapping("/api")
public class SysResourceController {
    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 查询
     */
    @ApiOperation("查询系统资源表")
    @RequestMapping(value = "/sys/resource/query", method = RequestMethod.GET)
    public ResponseData query(SysResource sysResource,
                                   @ApiParam(name = "page", value = "当前页") @RequestParam(defaultValue = "1") int page,
                                   @ApiParam(name = "pageSize", value = "页面大小") @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseData(sysResourceService.select(sysResource, page, pageSize));
    }

    /**
     *  脚本注册
     */
    @ApiOperation("脚本注册")
    @PostMapping("/sys/script/register")
    public void scriptDefinition(@RequestParam(value = "scripts", required = false)String scripts){
        sysResourceService.scriptRegister(scripts);
    }
}
