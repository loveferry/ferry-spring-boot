package cn.org.ferry.core.utils;

import cn.org.ferry.core.dto.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 网路工具类
 */

@Slf4j
public final class NetWorkUtils {
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    private static final String UNKNOWN = "unknown";
    private static final String USER_AGENT = "user-agent";
    public static final String DEFAULT_UNICODE = "utf-8";

    private NetWorkUtils(){}

    public static String getIpAddress(HttpServletRequest httpServletRequest){
        String ip = httpServletRequest.getHeader(X_FORWARDED_FOR);
        if(StringUtils.isNotEmpty(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)){
            String[] ips = ip.split(",");
            if(ips.length > 0){
                return ips[0];
            }
        }
        ip = httpServletRequest.getHeader(PROXY_CLIENT_IP);
        if(StringUtils.isNotEmpty(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)){
            return ip;
        }
        ip = httpServletRequest.getHeader(WL_PROXY_CLIENT_IP);
        if(StringUtils.isNotEmpty(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)){
            return ip;
        }
        ip = httpServletRequest.getHeader(HTTP_CLIENT_IP);
        if(StringUtils.isNotEmpty(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)){
            return ip;
        }
        ip = httpServletRequest.getHeader(HTTP_X_FORWARDED_FOR);
        if(StringUtils.isNotEmpty(ip) && !StringUtils.equalsIgnoreCase(UNKNOWN, ip)){
            return ip;
        }
        ip = httpServletRequest.getRemoteAddr();
        return ip;
    }

    public static String getUserAgent(HttpServletRequest httpServletRequest){
        return httpServletRequest.getHeader(USER_AGENT);
    }

    /**
     * 将 json 数据写入 response 中
     * @param response 响应
     * @param status 状态码
     * @param responseData 响应数据
     */
    public static void responseJsonWriter(HttpServletResponse response, int status, ResponseData responseData) throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding(DEFAULT_UNICODE);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(new ObjectMapper().writeValueAsString(responseData));
        printWriter.flush();
        printWriter.close();
    }
}
