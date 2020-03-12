package cn.org.ferry.core.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * created by 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Controller
public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private SysUserService sysUserService;

    /**
     * swagger 地址跳转
     */
    @RequestMapping(value = "/swagger")
    public String index() {
        logger.info("redirect:swagger-ui.html");
        return "redirect:swagger-ui.html";
    }

    /**
     * 登陆只能通过员工代码登陆
     * @param sysUser 员工代码和密码为必传项
     * @return 返回登陆信息
     */
    @LoginPass
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(HttpServletRequest request, @RequestBody SysUser sysUser){
        return sysUserService.login(request, sysUser);
    }
}
