package cn.org.ferry.core.security.dto;

/**
 * <p>登录类型
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/29 15:05
 */

public enum LoginType {
    FORM("表单"),

    JSON("json"),

    CAPTCHA("验证码");

    String description;

    LoginType(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
