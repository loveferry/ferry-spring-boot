package cn.org.ferry.mybatis.providers;


import cn.org.ferry.mybatis.helpers.MapperHelper;
import cn.org.ferry.mybatis.helpers.SqlHelper;
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

    public String select(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
        return SqlHelper.selectAllColumns(entityClass) +
                SqlHelper.fromTable(tableName(entityClass)) +
                SqlHelper.whereAllIfColumns(entityClass) +
                SqlHelper.orderByDefault(entityClass);
    }

    public String selectOne(MappedStatement ms) {
        return select(ms);
    }

    public String selectAll(MappedStatement ms) {
        return select(ms);
    }

    public String selectByPrimaryKey(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
        return SqlHelper.selectAllColumns(entityClass) +
                SqlHelper.fromTable(tableName(entityClass)) +
                SqlHelper.wherePKColumns(entityClass);
    }

    public String selectCount(MappedStatement ms) {
        return select(ms);
    }
}
