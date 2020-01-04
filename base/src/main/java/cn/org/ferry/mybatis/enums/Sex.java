package cn.org.ferry.mybatis.enums;

/**
 * <p>性别枚举类
 *
 * @author ferry ferry_sy@163.com
 * created by 2018/09/02 15:27
 */

public enum Sex {
    MALE("男"),

    FEMALE("女");

    private String description;

    Sex(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
