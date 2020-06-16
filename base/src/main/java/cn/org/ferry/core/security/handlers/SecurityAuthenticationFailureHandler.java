package cn.org.ferry.core.security.handlers;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.utils.NetWorkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>认证失败拦截器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/06/16 09:39
 */

public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(SecurityAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (response.isCommitted()) {
            logger.debug("Response has already been committed");
            return;
        }
        ResponseData responseData = new ResponseData();
        responseData.setCode(HttpStatus.UNAUTHORIZED.value());
        responseData.setSuccess(false);
        responseData.setMessage(exception.getMessage());
        NetWorkUtils.responseJsonWriter(response, HttpServletResponse.SC_OK, responseData);
    }
}
