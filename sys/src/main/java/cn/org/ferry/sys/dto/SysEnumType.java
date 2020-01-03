package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统用户 创建于 2019-07-28
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@Table(name = "sys_enum_type")
public class SysEnumType extends BaseDTO {
    /**
     * primary key
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long typeId;

    /**
     * 类型代码
     */
    private String typeCode;

    /**
     * Java类型(类全限定名)
     */
    private String javaType;

    /**
     * mysql类型
     */
    private String columnType;

    /**
     * 类型转化器(类全限定名)
     */
    private String typeHandler;
}
