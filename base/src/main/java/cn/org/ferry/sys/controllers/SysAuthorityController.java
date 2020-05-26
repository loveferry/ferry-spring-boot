package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysAuthority;
import cn.org.ferry.sys.service.SysAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Generate by code generator
 * 系统权限表 控制器
 */

@Api(tags = "系统权限表控制器", position = 100)
@RestController
@RequestMapping("/api")
public class SysAuthorityController {
	@Autowired
	private SysAuthorityService sysAuthorityService;

	/**
	 * 查询
	 */
	@ApiOperation("查询系统权限表")
	@RequestMapping("/sys/authority/query")
	public List<SysAuthority> query(SysAuthority sysAuthority,
								@ApiParam(name = "page", value = "当前页") @RequestParam(defaultValue = "1") int page,
								@ApiParam(name = "pageSize", value = "页面大小") @RequestParam(defaultValue = "10") int pageSize){
		return sysAuthorityService.select(sysAuthority, page, pageSize);
	}

}
