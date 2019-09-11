package cn.org.ferry.system.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>提示参数不为空的注解
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/10 11:03
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface NotNull {

}
