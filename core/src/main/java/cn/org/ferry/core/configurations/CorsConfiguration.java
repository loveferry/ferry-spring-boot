package cn.org.ferry.core.configurations;

import cn.org.ferry.core.exceptions.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * 注册跨域访问类
 */
@Configuration
public class CorsConfiguration {
    @Value("#{'${ferry.cors.allowedOrigins}'.split(',')}")
    private List<String> allowedOrigins;
    @Value("#{'${ferry.cors.allowedHeaders}'.split(',')}")
    private List<String> allowedHeaders;
    @Value("#{'${ferry.cors.allowedMethod}'.split(',')}")
    private List<String> allowedMethods;
    @Value("${ferry.cors.maxAge}")
    private Long maxAge;
    @Value("${ferry.cors.url}")
    private String url;

    @Bean
    public CorsFilter corsFilter() {
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置允许的网站域名，如果全允许则设为 *
        if(CollectionUtils.isEmpty(allowedOrigins)){
            config.addAllowedOrigin("*");
        }else{
            for (String allowedOrigin : allowedOrigins) {
                config.addAllowedOrigin(allowedOrigin);
            }
        }
        // 设置请求头允许范围
        if(CollectionUtils.isEmpty(allowedHeaders)){
            throw new CommonException("未设置允许的请求头!");
        }else{
            for (String allowedHeader : allowedHeaders) {
                config.addAllowedHeader(allowedHeader);
            }
        }
        // 设置请求方式的允许范围
        if(CollectionUtils.isEmpty(allowedMethods)){
            throw new CommonException("未设置允许的请求方式!");
        }else{
            for (String allowedMethod : allowedMethods) {
                config.addAllowedMethod(allowedMethod);
            }
        }
        // 设置预检请求的最大缓存范围
        config.setMaxAge(maxAge);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(url, config);
        return new CorsFilter(source);
    }
}
