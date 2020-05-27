package cn.org.ferry.sys.mapper;

import cn.org.ferry.core.mapper.Mapper;
import cn.org.ferry.sys.dto.SysGenerateTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGenerateTableMapper extends Mapper<SysGenerateTable> {

    /**
     * 查询当前数据库表名及其注释
     *      - mysql
     * @param tableName 表名/表注释
     * @return 返回表名，注释的集合
     */
    SysGenerateTable queryTablesByTableCommentForMysql(@Param("tableName") String tableName);

    /**
     * 查询指定表的字段信息
     *      - mysql
     * @param tableName 指定表名
     * @return 返回字段的信息
     */
    List<SysGenerateTable> queryTableColumnsByTableNameForMysql(@Param("tableName") String tableName);

    /**
     * 查询当前数据库表名及其注释
     *      - oracle
     * @param tableName 表名/表注释
     * @return 返回表名，注释的集合
     */
    SysGenerateTable queryTablesByTableCommentForOracle(@Param("tableName") String tableName);

    /**
     * 查询指定表的字段信息
     *      - oracle
     * @param tableName 指定表名
     * @return 返回字段的信息
     */
    List<SysGenerateTable> queryTableColumnsByTableNameForOracle(@Param("tableName") String tableName);

    /**
     * 查询表名
     *      - mysql
     */
    List<SysGenerateTable> queryTableNamesForMysql(@Param("tableName")String tableName);

    /**
     * 查询表名
     *      - mysql
     */
    List<SysGenerateTable> queryTableNamesForOracle(@Param("tableName")String tableName);


}
