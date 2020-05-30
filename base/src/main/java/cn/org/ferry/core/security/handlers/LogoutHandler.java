package cn.org.ferry.core.security.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>登出拦截器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/29 18:06
 */

public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(LogoutHandler.class);

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        logger.info("{} logout service deal.", user.getUsername());
    }
}
