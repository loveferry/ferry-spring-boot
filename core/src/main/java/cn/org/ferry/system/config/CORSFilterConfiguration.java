package cn.org.ferry.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 注册跨域访问类
 */
@Configuration
public class CORSFilterConfiguration {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置允许的网站域名，如果全允许则设为 *
//        config.addAllowedOrigin("http://localhost:8080");
//        config.addAllowedOrigin("http://104.198.87.241:8082");
//        config.addAllowedOrigin("http://104.198.87.241:8080");
        config.addAllowedOrigin("*");
        // 设置请求头允许范围
        config.addAllowedHeader("DNT");
        config.addAllowedHeader("X-Mx-ReqToken");
        config.addAllowedHeader("Keep-Alive");
        config.addAllowedHeader("User-Agent");
        config.addAllowedHeader("If-Modified-Since");
        config.addAllowedHeader("Cache-Control");
        config.addAllowedHeader("Content-Type");
        // 设置请求方式的允许范围
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.OPTIONS);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.PUT);
        // 设置预检请求的最大缓存范围
        config.setMaxAge(1800L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
