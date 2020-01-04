package cn.org.ferry.mybatis.annotations;


import cn.org.ferry.mybatis.version.DefaultNextVersion;
import cn.org.ferry.mybatis.version.NextVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Version {

    /**
     * 下一个版本号的算法，默认算法支持 Integer 和 Long，在原基础上 +1
     *
     * @return
     */
    Class<? extends NextVersion> nextVersion() default DefaultNextVersion.class;

}
