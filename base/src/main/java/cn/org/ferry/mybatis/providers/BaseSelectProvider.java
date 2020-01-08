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

    public String selectOne(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        return new StringBuilder()
                .append(SqlHelper.selectAllColumns(entityClass))
                .append(SqlHelper.fromTable(tableName(entityClass)))
                .append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()))
                .toString();
    }

    public String selectAll(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(tableName(entityClass)));

        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }

    public String selectByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        return new StringBuilder()
                .append(SqlHelper.selectAllColumns(entityClass))
                .append(SqlHelper.fromTable(tableName(entityClass)))
                .append(SqlHelper.wherePKColumns(entityClass))
                .toString();
    }

    public String selectCount(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        return sql.toString();
    }

    public String select(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }
}
