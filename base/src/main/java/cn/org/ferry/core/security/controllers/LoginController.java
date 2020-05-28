package cn.org.ferry.core.security.controllers;

import cn.org.ferry.core.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>登录控制器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/28 21:29
 */

@RestController
@RequestMapping("/login")
public class LoginController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录成功
     */
    @PostMapping("/success")
    public ResponseData success(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("user {} login success.", user.getUsername());
        List<User> list = new ArrayList<>(1);
        list.add(user);
        ResponseData responseData = new ResponseData(list);
        responseData.setMessage("登录成功");
        return responseData;
    }

    /**
     * 登录失败
     */
    @PostMapping("/failure")
    public ResponseData failure(){
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setCode(HttpStatus.UNAUTHORIZED.value());
        responseData.setMessage("认证失败");
        return responseData;
    }
}
