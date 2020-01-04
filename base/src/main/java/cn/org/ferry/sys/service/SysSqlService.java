package cn.org.ferry.sys.service;

import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.SysSql;

import java.sql.SQLException;
import java.util.Map;

public interface SysSqlService extends BaseService<SysSql> {
    /**
     * 查询数据源sql
     * @param sqlCode 必传，根据数据源代码查询数据源sql
     * @return 返回数据源sql
     */
    String querySqlBySqlCode(String sqlCode);

    /**
     * 执行给定的sql
     * @param sql 给定的sql语句
     * @param params sql语句对应的查询参数
     * @return 返回语句执行的返回结果
     */
    Object execute(String sql, Map<String, Object> params) throws SQLException;
}
