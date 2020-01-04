package cn.org.ferry.mybatis.code;

/**
 * 执行 SQL 的时机
 *
 * @author ferry
 */
public enum ORDER {
    AFTER, //insert 后执行 SQL
    BEFORE,//insert 前执行 SQL
    DEFAULT//使用全局配置
}
