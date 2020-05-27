package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import cn.org.ferry.mybatis.annotations.ColumnType;
import cn.org.ferry.mybatis.enums.IfOrNot;
import cn.org.ferry.mybatis.handlers.IfOrNotHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Generate by code generator
 * 系统角色表
 */

@Data
@Table(name = "SYS_ROLE")
public class SysRole extends BaseDTO {
	/**
	 * PK
	 */
	@Id
	@GeneratedValue
	private Double roleId;

	/**
	 * 角色编码
	 */
	@Length(max = 100)
	private String roleCode;

	/**
	 * 角色名称
	 */
	@Length(max = 100)
	private String roleName;

	/**
	 * 描述
	 */
	@Length(max = 200)
	private String description;

	/**
	 * 启用标志('Y','N')
	 */
	@ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
	private IfOrNot enabledFlag;
}
