package cn.org.ferry.system.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>实体类->数据库 属->字段映射关系
 *
 * @author ferry ferry_sy@163.com
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CaseFormat {
    com.google.common.base.CaseFormat value() default com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
}
