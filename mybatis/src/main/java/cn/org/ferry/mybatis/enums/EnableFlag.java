package cn.org.ferry.mybatis.enums;

/**
 * created by 2018-12-21
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

public enum EnableFlag {
    Y("启用"),

    N("禁用");

    private String description;

    EnableFlag(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
