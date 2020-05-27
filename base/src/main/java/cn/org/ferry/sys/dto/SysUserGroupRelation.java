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
 * 用户和用户组关系表
 */

@Data
@Table(name = "SYS_USER_GROUP_RELATION")
public class SysUserGroupRelation extends BaseDTO {
	@Id
	@GeneratedValue
	private Long relationId;

	/**
	 * 用户编码
	 */
	@Length(max = 100)
	private String userCode;

	/**
	 * 用户组编码
	 */
	@Length(max = 100)
	private String groupCode;

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
