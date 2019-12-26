package cn.org.ferry.system.utils;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/08/11
 * @description 属性工具类,在spring注册容器阶段不能通过此工具类获取属性
 */
public final class ConfigUtil {
    public static final String DEFAULT_CHARSET = "UTF-8";

    private static ConfigurableApplicationContext applicationContext;

    private ConfigUtil(){}

    public static void init(ConfigurableApplicationContext configurableApplicationContext){
        if(null == applicationContext){
            synchronized (ConfigurableApplicationContext.class){
                if(null == applicationContext){
                    applicationContext = configurableApplicationContext;
                }
            }
        }
    }

    public static String getProperty(String name){
        if(null == applicationContext || null == applicationContext.getEnvironment()){
            return null;
        }
        return applicationContext.getEnvironment().getProperty(name);
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> cls){
        return applicationContext.getBean(name, cls);
    }

    public static <T> T getBean(Class<T> cls){
        return applicationContext.getBean(cls);
    }

    public static Object getBean(String name, Object ... args){
        return applicationContext.getBean(name, args);
    }

    public static <T> T getBean(Class<T> cls, Object ... args){
        return applicationContext.getBean(cls, args);
    }


}
