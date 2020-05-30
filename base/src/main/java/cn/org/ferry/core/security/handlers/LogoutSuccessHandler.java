package cn.org.ferry.core.security.handlers;

import cn.org.ferry.core.dto.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/29 18:12
 */

public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(LogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        logger.info("{} logout service success.", user.getUsername());
        ResponseData responseData = new ResponseData();
        responseData.setMessage("登出成功");
        responseData.setCode(HttpStatus.OK.value());
        responseJsonWriter(response, responseData);
    }

    private static void responseJsonWriter(HttpServletResponse response, ResponseData responseData) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String resBody = objectMapper.writeValueAsString(responseData);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }
}
