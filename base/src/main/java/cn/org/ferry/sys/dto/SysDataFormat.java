package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据格式化实体类 2019-07-13
 * @author ferry ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@Table(name = "sys_sql")
public class SysDataFormat extends BaseDTO {
    /**
     * primary key
     */
    @Id
    @GeneratedValue
    private Long dataFormatId;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 格式化表达式
     */
    private String dataFormat;

    /**
     * 格式化说明
     */
    private String description;
}
