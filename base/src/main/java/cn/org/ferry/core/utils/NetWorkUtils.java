package cn.org.ferry.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

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
}
