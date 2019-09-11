package cn.org.ferry.system.config;

import cn.org.ferry.system.aspects.AspectDemo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring AOP 配置类
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {
    @Bean
    public AspectDemo aspectDemo() {
        return new AspectDemo();
    }
}
