package cn.org.ferry.mybatis.enums;

/**
 * created by 2019-07-21
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

public enum IfOrNot {
    Y("是"),

    N("否");

    private String description;

    IfOrNot(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
