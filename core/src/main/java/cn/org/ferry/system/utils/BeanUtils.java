package cn.org.ferry.system.utils;

import cn.org.ferry.system.dto.BaseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体类的工具类
 */

public final class BeanUtils {
    public static final Integer INTEGER_0 = 0;
    public static final Long LONG_0 = 0L;
    public static final Double DOUBLE_0 = 0D;
    public static final String EMPTY = "";

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils(){}

    public static <T extends BaseDTO> List<Map<String, Object>> transfer(List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return new ArrayList<>(0);
        }
        List<Map<String, Object>> resultList = new ArrayList<>(list.size());
        try {
            for (T dto : list) {
                Class cls = dto.getClass();
                Field[] fields = getFields(cls);
                if(null == fields || fields.length == 0){
                    logger.error("该实体类没有任何属性.");
                    return new ArrayList<>(0);
                }
                Map<String, Object> map = new HashMap<>(fields.length);
                for(int i = 0; i < fields.length; i++){
                    String fieldName = fields[i].getName();
                    Method method = null;
                    try {
                        method = cls.getMethod(getMethodName(fieldName), null);
                        map.put(fieldName, method.invoke(dto,null));
                    } catch (NoSuchMethodException e) {
                        logger.info("类 {} 没有属性 {} 的 get 方法", cls.getName(), fieldName);
                    }
                }
                resultList.add(map);
            }
            return resultList;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    private static Field[] getFields(Class cls){
        Field[] fs = cls.getDeclaredFields();
        if(null == fs || fs.length == 0){
            fs = new Field[0];
        }
        if(cls != BaseDTO.class){
            Field[] superFields = getFields(cls.getSuperclass());
            if(null == superFields || superFields.length == 0){
                superFields = new Field[0];
            }
            Field[] fields = new Field[fs.length+superFields.length];
            for(int i = 0; i < fs.length; i++){
                fields[i] = fs[i];
            }
            for(int i = 0; i < superFields.length; i++){
                fields[i+fs.length] = superFields[i];
            }
            fs = fields;
        }
        List<Field> list = new ArrayList<>(fs.length);
        for (Field f : fs) {
            if("private".equals(Modifier.toString(f.getModifiers()))){
                list.add(f);
            }
        }
        fs = new Field[list.size()];
        list.toArray(fs);
        return fs;
    }

    public static String getMethodName(String fieldName){
        return "get"+transferFirstLetter(fieldName);
    }

    public static String setMethodName(String fieldName){
        return "set"+transferFirstLetter(fieldName);
    }

    private static String transferFirstLetter(String letter){
        if(null == letter || letter.length() == 0){
            throw new NullPointerException("字符串为空!");
        }
        return letter.substring(0,1).toUpperCase().concat(letter.substring(1));
    }

    public static boolean isIntegerNumber(Object o){
        if(null == o){
            return false;
        }
        try{
            Long.valueOf(String.valueOf(o));
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isFloatNumber(Object o){
        if(null == o){
            return false;
        }
        try{
            Double.valueOf(String.valueOf(o));
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isDate(Object o){
        if(null == o){
            return false;
        }
        if(o instanceof Date){
            return true;
        }
        return false;
    }

    public static <T> T ifnull(T t, T defaultValue){
        return t == null ? defaultValue : t;
    }
}
