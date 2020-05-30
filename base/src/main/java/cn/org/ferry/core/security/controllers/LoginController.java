package cn.org.ferry.core.security.controllers;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.utils.NetWorkUtils;
import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.LogLoginService;
import cn.org.ferry.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>登录控制器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/28 21:29
 */

@RestController
public class LoginController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private SysUserService sysUserService;
    @Resource
    private LogLoginService logLoginService;

    /**
     * 登录成功
     */
    @Transactional
    @PostMapping("/login/success")
    public ResponseData success(HttpServletRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("user {} login success.", user.getUsername());

        SysUser sysUser = sysUserService.queryForLoginSuccess(user.getUsername());
        logLoginService.insertLogLogin(sysUser.getUserCode(), NetWorkUtils.getIpAddress(request), NetWorkUtils.getUserAgent(request));

        List<SysUser> list = new ArrayList<>(1);
        list.add(sysUser);
        ResponseData responseData = new ResponseData(list);
        responseData.setMessage("登录成功");
        return responseData;
    }

    /**
     * 登录失败
     */
    @PostMapping("/login/failure")
    public ResponseData failure(){
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setCode(HttpStatus.UNAUTHORIZED.value());
        responseData.setMessage("认证失败");
        return responseData;
    }
}
