package cn.org.ferry.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 跳过登陆即可访问的注解
 * 在方法上，可以跳过token验证直接访问方法
 * 在类上，该控制器中所有的方法都可以跳过token验证直接访问
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginPass {
}
