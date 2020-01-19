package cn.org.ferry.mybatis.providers;


import cn.org.ferry.mybatis.helpers.EntityHelper;
import cn.org.ferry.mybatis.helpers.MapperHelper;
import cn.org.ferry.mybatis.helpers.SqlHelper;
import cn.org.ferry.mybatis.utils.MetaObjectUtil;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * <p>BaseDeleteProvider实现类，基础方法实现类
 *
 * @author ferry ferry_sy@163.com
 */

public class BaseDeleteProvider extends MapperTemplate {

    public BaseDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String delete(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        //如果设置了安全删除，就不允许执行不带查询条件的 delete 方法
        if (getConfig().isSafeDelete()) {
            sql.append(SqlHelper.notAllNullParameterCheck("_parameter", EntityHelper.getColumns(entityClass)));
        }
        sql.append(SqlHelper.deleteFromTable(tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass));
        return sql.toString();
    }

    public String deleteByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(tableName(entityClass)));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }
}
