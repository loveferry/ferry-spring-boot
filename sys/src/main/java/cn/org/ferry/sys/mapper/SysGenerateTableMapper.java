package cn.org.ferry.sys.mapper;

import cn.org.ferry.core.mapper.Mapper;
import cn.org.ferry.sys.dto.SysGenerateTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGenerateTableMapper extends Mapper<SysGenerateTable> {
    /**
     * 查询当前数据库表名及其注释
     * @param tableName 表名/表注释
     * @return 返回表名，注释的集合
     */
    List<SysGenerateTable> queryTablesLikeTableComment(@Param("tableName") String tableName);

    /**
     * 查询当前数据库表名及其注释
     * @param tableName 表名/表注释
     * @return 返回表名，注释的集合
     */
    SysGenerateTable queryTablesByTableComment(@Param("tableName") String tableName);

    /**
     * 查询指定表的字段信息
     * @param tableName 指定表名
     * @return 返回字段的信息
     */
    List<SysGenerateTable> queryTableColumnsByTableName(@Param("tableName") String tableName);
}
