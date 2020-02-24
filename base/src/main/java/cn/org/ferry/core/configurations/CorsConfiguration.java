package cn.org.ferry.core.configurations;

import cn.org.ferry.core.dto.CorsProp;
import cn.org.ferry.core.exceptions.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * 注册跨域访问类
 */
@Configuration
public class CorsConfiguration {
    @Autowired
    private CorsProp corsProp;
    @Bean
    public CorsFilter corsFilter() {
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置允许的网站域名，如果全允许则设为 *
        if(CollectionUtils.isEmpty(corsProp.getAllowedOrigins())){
            config.addAllowedOrigin("*");
        }else{
            for (String allowedOrigin : corsProp.getAllowedOrigins()) {
                config.addAllowedOrigin(allowedOrigin);
            }
        }
        // 设置请求头允许范围
        if(CollectionUtils.isEmpty(corsProp.getAllowedHeaders())){
            throw new CommonException("未设置允许的请求头!");
        }else{
            for (String allowedHeader : corsProp.getAllowedHeaders()) {
                config.addAllowedHeader(allowedHeader);
            }
        }
        // 设置请求方式的允许范围
        if(CollectionUtils.isEmpty(corsProp.getAllowedMethods())){
            throw new CommonException("未设置允许的请求方式!");
        }else{
            for (String allowedMethod : corsProp.getAllowedMethods()) {
                config.addAllowedMethod(allowedMethod);
            }
        }
        // 设置预检请求的最大缓存范围
        config.setMaxAge(corsProp.getMaxAge());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProp.getUrl(), config);
        return new CorsFilter(source);
    }
}
