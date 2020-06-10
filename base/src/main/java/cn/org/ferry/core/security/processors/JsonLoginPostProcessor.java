package cn.org.ferry.core.security.processors;

import cn.org.ferry.core.security.dto.LoginType;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;

/**
 * <p>登录前置处理器  json数据提交登录
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/31 16:59
 */

@Component("jsonLoginPostProcessor")
public class JsonLoginPostProcessor implements LoginPostProcessor {
    private ThreadLocal<Object> password = new ThreadLocal<>();

    @Override
    public LoginType loginType() {
        return LoginType.JSON;
    }

    @Override
    public String username(ServletRequest request) {
        JSONObject requestBody = getRequestBody(request);
        password.set(requestBody.get(PASSWORD));
        return String.valueOf(requestBody.get(USERNAME));
    }

    @Override
    public String password(ServletRequest request) {
        return String.valueOf(password.get());
    }
}
