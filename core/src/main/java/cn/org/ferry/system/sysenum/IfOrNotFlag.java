package cn.org.ferry.system.sysenum;

/**
 * created by 2019-07-21
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

public enum IfOrNotFlag {
    Y("是"),

    N("否");

    private String description;

    IfOrNotFlag(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
