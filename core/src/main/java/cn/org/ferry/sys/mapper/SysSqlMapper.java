package cn.org.ferry.sys.mapper;

import org.apache.ibatis.annotations.Param;

public interface SysSqlMapper{
    /**
     * 查询数据源sql
     * @param sqlCode 必传，根据数据源代码查询数据源sql
     * @return 返回数据源sql
     */
    String querySqlBySqlCode(@Param("sqlCode") String sqlCode);
}
