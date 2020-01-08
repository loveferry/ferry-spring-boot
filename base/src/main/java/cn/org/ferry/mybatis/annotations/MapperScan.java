package cn.org.ferry.mybatis.annotations;

import cn.org.ferry.mybatis.autoconfigure.MapperFactoryBean;
import cn.org.ferry.mybatis.autoconfigure.MapperScannerRegistrar;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定需要扫描的 packages 的注解
 *
 * 基于 org.mybatis.spring.annotation.MapperScan 重写；
 * 主要加了两个属性，并使用自定义的注解扫描器 {@link MapperScannerRegistrar} 完成 mapper 注册
 *
 * @author ferry ferry_sy@163.com
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MapperScannerRegistrar.class)
public @interface MapperScan {
    /**
     * Base packages to scan for MyBatis interfaces. Note that only interfaces
     * with at least one method will be registered; concrete classes will be
     * ignored.
     */
    String[] basePackages() default {};
}
