package cn.org.ferry.system.utils;

import cn.org.ferry.system.dto.BaseDTO;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 实体类的工具类
 */

@Slf4j
public class BeanUtils {
    public static <T extends BaseDTO> List<Map<String, Object>> transfer(List<T> list){
        if(null == list || list.size() == 0){
            return new ArrayList<>(0);
        }
        List<Map<String, Object>> resultList = new ArrayList<>(list.size());
        try {
            for (T dto : list) {
                Class cls = dto.getClass();
                Field[] fields = getFields(cls);
                if(null == fields || fields.length == 0){
                    log.error("该实体类没有任何属性.");
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
                        log.info("类 {} 没有属性 {} 的 get 方法", cls.getName(), fieldName);
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
            long l = Long.parseLong(o.toString());
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
            double l = Double.parseDouble(o.toString());
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
}
