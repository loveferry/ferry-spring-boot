package cn.org.ferry.sys.mapper;

import cn.org.ferry.sys.dto.SysSql;
import cn.org.ferry.system.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SysSqlMapper extends BaseMapper<SysSql> {
    /**
     * 查询数据源sql
     * @param sqlCode 必传，根据数据源代码查询数据源sql
     * @return 返回数据源sql
     */
    String querySqlBySqlCode(@Param("sqlCode") String sqlCode);
}
