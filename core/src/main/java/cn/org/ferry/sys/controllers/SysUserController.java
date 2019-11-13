package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import cn.org.ferry.system.annotations.LoginPass;
import cn.org.ferry.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登陆只能通过员工代码登陆
     * @param sysUser 员工代码和密码为必传项
     * @return 返回登陆信息
     */
    @LoginPass
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseData login(HttpServletRequest request, @RequestBody SysUser sysUser){
        return sysUserService.login(request, sysUser);
    }
}
