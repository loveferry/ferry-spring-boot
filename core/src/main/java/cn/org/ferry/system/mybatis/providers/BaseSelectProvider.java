package cn.org.ferry.system.mybatis.providers;

import cn.org.ferry.system.mybatis.helper.MapperHelper;
import cn.org.ferry.system.mybatis.helper.MapperTemplate;
import cn.org.ferry.system.mybatis.helper.SqlHelper;
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
        final Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));

        // 逻辑删除的未删除查询条件
        sql.append("<where>");
        sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        sql.append("</where>");

        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }


    public String selectOne2(){
        return "select * from chinese_people where id = 1";
    }
}
