package cn.org.ferry.mybatis.autoconfigure;

import cn.org.ferry.mybatis.annotations.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * mapper 扫描注册器
 *
 * 继承 {@link ImportBeanDefinitionRegistrar} 类实现动态注册成 spring bean
 *
 * 基于 org.mybatis.spring.annotation.MapperScannerRegistrar 重写，
 * 使用自定义的 mapper 扫描 {@link ClassPathMapperScanner} 完成 spring bean 的注册
 *
 * @author ferry ferry_sy@163.com
 */

public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    /**
     * 日志对象
     */
    public static final Logger logger = LoggerFactory.getLogger(MapperScannerRegistrar.class);

    /**
     * 资源加载器
     */
    private ResourceLoader resourceLoader;

    /**
     * 环境变量
     */
    private Environment environment;

    /**
     * 实现注册 BeanDefinitions 的方法
     * @param importingClassMetadata 对注解的抽象，使用该对象进行注解的解析
     * @param registry BeanDefinition 注册器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        logger.info("Begin register mapper BeanDefinition.");

        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName()));
        ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
        // this check is needed in Spring 3.1
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }

        List<String> basePackages = new ArrayList<>();
        for (String pkg : annoAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        try {
            scanner.setMapperProperties(this.environment);
        } catch (Exception e) {
            logger.warn("只有 Spring Boot 环境中可以通过 Environment(配置文件,环境变量,运行参数等方式) 配置通用 Mapper，" +
                    "其他环境请通过 @MapperScan 注解中的 mapperHelperRef 或 properties 参数进行配置!", e);
        }
        scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(basePackages));

        logger.info("End register mapper BeanDefinition.");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
