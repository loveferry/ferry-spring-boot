package cn.org.ferry.core.security.filters;

import cn.org.ferry.core.security.dto.LoginType;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;

/**
 * <p>默认的登录处理器，form表单类型
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/31 16:54
 */

@Component("loginPostProcessor")
public class DefaultLoginPostProcessor implements LoginPostProcessor{
    @Override
    public LoginType loginType() {
        return LoginType.FORM;
    }

    @Override
    public String username(ServletRequest request) {
        return request.getParameter(USERNAME);
    }

    @Override
    public String password(ServletRequest request) {
        return request.getParameter(PASSWORD);
    }
}
