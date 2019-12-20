package cn.org.ferry.system.mybatis.providers;

import cn.org.ferry.system.mybatis.helper.MapperHelper;
import cn.org.ferry.system.mybatis.helper.MapperTemplate;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * <p>BaseSelectProvider实现类，基础方法实现类
 *
 * @author ferry ferry_sy@163.com
 */

public class BaseSelectProvider extends MapperTemplate {

    public BaseSelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectOne(MappedStatement ms) {
        return "select * from chinese_people";
    }

    public String selectAll(MappedStatement ms) {
        return "select * from chinese_people";
    }


}
