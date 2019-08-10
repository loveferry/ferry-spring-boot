package cn.org.ferry.system.utils;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/08/11
 * @description 属性工具类,在spring注册容器阶段不能通过此工具类获取属性
 */
public final class PropertiesUtils {
    private static Environment environment;

    private PropertiesUtils(){}

    public static void init(ConfigurableApplicationContext applicationContext){
        if(null == environment){
            synchronized (Environment.class){
                if(null == environment){
                    environment = applicationContext.getEnvironment();
                }
            }
        }
    }

    public static String getProperty(String name){
        if(null == environment){
            return null;
        }
        return environment.getProperty(name);
    }
}
