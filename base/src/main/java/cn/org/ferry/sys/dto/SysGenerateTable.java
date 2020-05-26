package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import cn.org.ferry.mybatis.annotations.ColumnType;
import cn.org.ferry.mybatis.enums.IfOrNot;
import cn.org.ferry.mybatis.handlers.IfOrNotHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 系统用户 创建于 2019-07-28
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@Table(name = "sys_generate_table")
public class SysGenerateTable extends BaseDTO {
    /**
     * primary key
     */
    @Id
    @GeneratedValue
    private Long generateId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 控制器生成标志
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
    private IfOrNot controllerFlag;

    /**
     * 实体类生成标志
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
    private IfOrNot entityFlag;

    /**
     * 业务接口生成标志
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
    private IfOrNot serviceFlag;

    /**
     * 业务实现生成标志
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
    private IfOrNot serviceImplFlag;

    /**
     * mybatis接口生成标志
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
    private IfOrNot mapperJavaFlag;

    /**
     * sql层生成标志
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
    private IfOrNot mapperXmlFlag;

    @Transient
    private String tableComment;

    @Transient
    private String columnName;

    @Transient
    private String dataType;

    @Transient
    private Long characterMaximumLength;

    @Transient
    private String columnType;

    @Transient
    private String columnKey;

    @Transient
    private String columnComment;

    @Transient
    private String nullable;

    @Transient
    private String entityName;

    @Transient
    private String mapperJavaName;

    @Transient
    private String mapperXmlName;

    @Transient
    private String serviceName;

    @Transient
    private String serviceImplName;

    @Transient
    private String controllerName;

    @Transient
    private String projectPath;

    @Transient
    private String packagePath;
}
