package cn.org.ferry.core.utils;

import java.text.SimpleDateFormat;

public final class ConstantUtils {

    public interface StringConstant{
        /**
         * 空字符串
         */
        String EMPTY = "";

        /**
         * 分号
         */
        String SEMICOLON = ";";

        /**
         * 右括号
         */
        String RIGHT_PARENTHESIS = ")";

        /**
         * 右括号
         */
        String LEFT_PARENTHESIS = "(";

        /**
         * 逗号
         */
        String COMMA = ",";
    }

    public interface DateFormat{
        SimpleDateFormat YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * 数据库常量
     */
    public interface DataBaseConstant{
        /**
         * mysql
         */
        String MYSQL = "MYSQL";

        /**
         * oracle
         */
        String ORACLE = "ORACLE";
    }

    /**
     * 脚本类型
     */
    public interface ScriptType{
        /**
         * 资源定义
         */
        String RESOURCE_DEFINITION = "resource_definition";

        /**
         * 资源分配
         */
        String RESOURCE_ALLOCATION = "resource_allocation";

        /**
         * 权限定义
         */
        String AUTHORITY_DEFINITION = "authority_definition";

        /**
         * 权限分配
         */
        String AUTHORITY_ALLOCATION = "authority_allocation";


    }
}
