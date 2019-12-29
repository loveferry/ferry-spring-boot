package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import cn.org.ferry.system.annotations.LoginPass;
import cn.org.ferry.system.dto.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户控制器")
@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登陆只能通过员工代码登陆
     * @param sysUser 员工代码和密码为必传项
     * @return 返回登陆信息
     */
    @ApiOperation("登陆")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "_token", required = false)
    )
    @LoginPass
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseData login(HttpServletRequest request, @RequestBody SysUser sysUser){
        return sysUserService.login(request, sysUser);
    }
}
