package cn.org.ferry.system.config;

import cn.org.ferry.system.mybatis.interceptors.SelectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置类
 */

@Configuration
public class MybatisConfiguration {
    @Bean
    public SelectInterceptor selectInterceptor() {
        return new SelectInterceptor();
    }
}
