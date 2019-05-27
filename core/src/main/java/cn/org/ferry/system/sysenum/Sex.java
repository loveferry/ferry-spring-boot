package cn.org.ferry.system.sysenum;

/**
 * created by 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
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
