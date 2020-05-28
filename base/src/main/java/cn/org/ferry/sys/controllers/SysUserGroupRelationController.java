package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysUserGroupRelation;
import cn.org.ferry.sys.service.SysUserGroupRelationService;
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
 * 用户和用户组关系表 控制器
 */

@Api(tags = "用户和用户组关系表控制器", position = 300)
@RestController
@RequestMapping("/api")
public class SysUserGroupRelationController {
	@Autowired
	private SysUserGroupRelationService sysUserGroupRelationService;

	/**
	 * 查询
	 */
	@ApiOperation(value = "查询用户和用户组关系表", position = 310)
	@RequestMapping(value = "/sys/user/group/relation/query", method = RequestMethod.GET)
	public List<SysUserGroupRelation> query(SysUserGroupRelation sysUserGroupRelation,
								@ApiParam(name = "page", value = "当前页") @RequestParam(defaultValue = "1") int page,
								@ApiParam(name = "pageSize", value = "页面大小") @RequestParam(defaultValue = "10") int pageSize){
		return sysUserGroupRelationService.select(sysUserGroupRelation, page, pageSize);
	}

}
