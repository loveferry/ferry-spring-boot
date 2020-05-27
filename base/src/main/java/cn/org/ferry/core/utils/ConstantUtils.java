package cn.org.ferry.core.utils;

import java.text.SimpleDateFormat;

public final class ConstantUtils {

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
}
