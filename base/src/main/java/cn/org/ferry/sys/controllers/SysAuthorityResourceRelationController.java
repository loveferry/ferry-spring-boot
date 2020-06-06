package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysAuthorityResourceRelation;
import cn.org.ferry.sys.service.SysAuthorityResourceRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Generate by code generator
 * 权限资源关系表 控制器
 */

@Api(tags = "权限资源关系表控制器", position = 100)
@RestController
@RequestMapping("/api")
public class SysAuthorityResourceRelationController {
    @Autowired
    private SysAuthorityResourceRelationService sysAuthorityResourceRelationService;

    /**
     * 查询
     */
    @ApiOperation("查询权限资源关系表")
    @RequestMapping(value = "/sys/authority/resource/rel/query", method = RequestMethod.GET)
    public List<SysAuthorityResourceRelation> query(SysAuthorityResourceRelation sysAuthorityResourceRelation,
                                                    @ApiParam(name = "page", value = "当前页") @RequestParam(defaultValue = "1") int page,
                                                    @ApiParam(name = "pageSize", value = "页面大小") @RequestParam(defaultValue = "10") int pageSize) {
        return sysAuthorityResourceRelationService.select(sysAuthorityResourceRelation, page, pageSize);
    }

}
