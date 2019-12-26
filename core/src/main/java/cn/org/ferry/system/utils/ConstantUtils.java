package cn.org.ferry.system.utils;

import java.text.SimpleDateFormat;

public final class ConstantUtils {
    public static final String _TOKEN = "_token";

    public interface DateFormat{
        SimpleDateFormat YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");
    }

    public interface BaseController{
        String TEST = "test";
    }
}
