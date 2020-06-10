package cn.org.ferry.core.security.processors;

import cn.org.ferry.core.security.dto.LoginType;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <p>登录前置处理器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/29 15:05
 */

public interface LoginPostProcessor {
    /**
     * 日志对象
     */
    Logger logger = LoggerFactory.getLogger(LoginPostProcessor.class);

    /**
     * key    用户名
     */
    String USERNAME = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

    /**
     * key    密码
     */
    String PASSWORD = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;

    /**
     * 获取登录类型
     */
    LoginType loginType();

    /**
     * 获取用户名
     */
    String username(ServletRequest request);

    /**
     * 获取密码
     */
    String password(ServletRequest request);

    default JSONObject getRequestBody(ServletRequest request){
        return JSONObject.parseObject(getBody(new HttpServletRequestWrapper((HttpServletRequest) request)));
    }

    default String getBody(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = request.getReader()){
            String msg;
            while (null != (msg = br.readLine())){
                sb.append(msg);
            }
        }catch (IOException e){
            logger.error("request body read error", e);
        }
        return sb.toString();
    }
}
