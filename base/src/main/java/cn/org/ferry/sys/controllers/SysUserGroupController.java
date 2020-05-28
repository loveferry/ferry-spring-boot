package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysUserGroup;
import cn.org.ferry.sys.service.SysUserGroupService;
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
 * 系统用户组表 控制器
 */

@Api(tags = "系统用户组表控制器", position = 200)
@RestController
@RequestMapping("/api")
public class SysUserGroupController {
	@Autowired
	private SysUserGroupService sysUserGroupService;

	/**
	 * 查询
	 */
	@ApiOperation(value = "查询系统用户组表", position = 210)
	@RequestMapping(value = "/sys/user/group/query", method = RequestMethod.GET)
	public List<SysUserGroup> query(SysUserGroup sysUserGroup,
								@ApiParam(name = "page", value = "当前页") @RequestParam(defaultValue = "1") int page,
								@ApiParam(name = "pageSize", value = "页面大小") @RequestParam(defaultValue = "10") int pageSize){
		return sysUserGroupService.select(sysUserGroup, page, pageSize);
	}

}
