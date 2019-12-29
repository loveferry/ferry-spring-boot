package cn.org.ferry.system.mybatis.providers;

import cn.org.ferry.system.mybatis.helper.MapperHelper;
import cn.org.ferry.system.mybatis.helper.MapperTemplate;
import cn.org.ferry.system.mybatis.helper.SqlHelper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * <p>BaseUpdateProvider实现类，基础方法实现类
 *
 * @author ferry ferry_sy@163.com
 */

public class BaseUpdateProvider extends MapperTemplate {
    public BaseUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String updateByPrimaryKey(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, false, false));
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, true, isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
    }
}