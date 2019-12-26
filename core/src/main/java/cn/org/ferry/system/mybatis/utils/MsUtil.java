package cn.org.ferry.system.mybatis.utils;

import cn.org.ferry.system.exception.MybatisException;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.SoftCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @author ferry
 */

public class MsUtil {

    private static final Cache CLASS_CACHE = new SoftCache(new PerpetualCache("MAPPER_CLASS_CACHE"));

    /**
     * 根据msId获取接口类
     */
    public static Class<?> getMapperClass(String msId) {
        if (!msId.contains(".")) {
            throw new MybatisException("当前 MappedStatement 的id=" + msId + ",不符合MappedStatement的规则!");
        }
        String mapperClassStr = msId.substring(0, msId.lastIndexOf("."));
        //由于一个接口中的每个方法都会进行下面的操作，因此缓存
        Class<?> mapperClass = (Class<?>) CLASS_CACHE.getObject(mapperClassStr);
        if(mapperClass != null){
            return mapperClass;
        }
        ClassLoader[] classLoader = new ClassLoader[]{Thread.currentThread().getContextClassLoader(), MsUtil.class.getClassLoader()};

        for (ClassLoader cl : classLoader) {
            if (null != cl) {
                try {
                    mapperClass = Class.forName(mapperClassStr, true, cl);
                    if (mapperClass != null) {
                        break;
                    }
                } catch (ClassNotFoundException e) {
                    // we'll ignore this until all class loaders fail to locate the class
                }
            }
        }
        if (mapperClass == null) {
            throw new MybatisException("class loaders failed to locate the class " + mapperClassStr);
        }
        CLASS_CACHE.putObject(mapperClassStr, mapperClass);
        return mapperClass;
    }

    /**
     * 获取执行的方法名
     *
     * @param ms
     * @return
     */
    public static String getMethodName(MappedStatement ms) {
        return getMethodName(ms.getId());
    }

    /**
     * 获取执行的方法名
     */
    public static String getMethodName(String msId) {
        return msId.substring(msId.lastIndexOf(".") + 1);
    }

}
