package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysUserRoleRelation;
import cn.org.ferry.sys.service.SysUserRoleRelationService;
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
 * 用户权限关系表 控制器
 */

@Api(tags = "用户权限关系表控制器", position = 500)
@RestController
@RequestMapping("/api")
public class SysUserRoleRelationController {
	@Autowired
	private SysUserRoleRelationService sysUserRoleRelationService;

	/**
	 * 查询
	 */
	@ApiOperation(value = "查询用户权限关系表", position = 510)
	@RequestMapping(value = "/sys/user/role/relation/query", method = RequestMethod.GET)
	public List<SysUserRoleRelation> query(SysUserRoleRelation sysUserRoleRelation,
								@ApiParam(name = "page", value = "当前页") @RequestParam(defaultValue = "1") int page,
								@ApiParam(name = "pageSize", value = "页面大小") @RequestParam(defaultValue = "10") int pageSize){
		return sysUserRoleRelationService.select(sysUserRoleRelation, page, pageSize);
	}

}
