package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import cn.org.ferry.mybatis.annotations.ColumnType;
import cn.org.ferry.mybatis.handlers.IfOrNotHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Generate by code generator
 * 用户权限关系表
 */

@Data
@Table(name = "SYS_USER_ROLE_RELATION")
public class SysUserRoleRelation extends BaseDTO {
	/**
	 * PK
	 */
	@Id
	@GeneratedValue
	private Double relationId;

	/**
	 * 来源类型(SYS_USER/SYS_USER_GROUP)
	 */
	@Length(max = 100)
	private String sourceType;

	/**
	 * 来源编码
	 */
	@Length(max = 100)
	private String sourceCode;

	/**
	 * 角色编码
	 */
	@Length(max = 100)
	private String roleCode;

	/**
	 * 描述
	 */
	@Length(max = 200)
	private String description;

	/**
	 * 启用标志('Y','N')
	 */
	@ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
	private String enabledFlag;
}
