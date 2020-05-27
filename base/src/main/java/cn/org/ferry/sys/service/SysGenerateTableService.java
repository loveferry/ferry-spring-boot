package cn.org.ferry.sys.service;

import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.SysGenerateTable;
import cn.org.ferry.sys.exceptions.FileException;

import java.util.List;

public interface SysGenerateTableService extends BaseService<SysGenerateTable> {

    /**
     * 查询当前数据库表名及其注释
     * @param tableName 表名/表注释
     * @return 返回表名，注释的集合
     */
    SysGenerateTable queryTablesByTableComment(String tableName);

    /**
     * 查询指定表的字段信息
     * @param tableName 指定表名
     * @return 返回字段的信息
     */
    List<SysGenerateTable> queryTableColumnsByTableName(String tableName);

    /**
     * 代码生成器
     */
    void generate(SysGenerateTable sysGenerateTable) throws FileException;

    /**
     * 查询表名
     */
    List<SysGenerateTable> queryTableNames(String tableName, int page, int pageSize);
}
