package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据源 创建于 2019-07-13
 * @author ferry ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@Table(name = "sys_sql")
public class SysSql extends BaseDTO {
    /**
     * primary key
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long sqlId;

    /**
     * 代码
     */
    private String sqlCode;

    /**
     * 名称
     */
    private String sqlName;

    /**
     * 数据源
     */
    private String sql;

    /**
     * 说明
     */
    private String description;
}
