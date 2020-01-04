package cn.org.ferry.mybatis.utils;

import cn.org.ferry.mybatis.exceptions.CommonMapperException;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Method;

/**
 * @author ferry
 */
public class MetaObjectUtil {
    public static Method method;

    private static final String METHOD_NAME = "forObject";

    static {
        try {
            Class<?> metaClass = SystemMetaObject.class;
            method = metaClass.getDeclaredMethod(METHOD_NAME, Object.class);
        } catch (Exception e1) {
            try {
                Class<?> metaClass = MetaObject.class;
                method = metaClass.getDeclaredMethod(METHOD_NAME, Object.class);
            } catch (Exception e2) {
                throw new CommonMapperException(e2);
            }
        }

    }

    public static MetaObject forObject(Object object) {
        try {
            return (MetaObject) method.invoke(null, object);
        } catch (Exception e) {
            throw new CommonMapperException(e);
        }
    }

}
