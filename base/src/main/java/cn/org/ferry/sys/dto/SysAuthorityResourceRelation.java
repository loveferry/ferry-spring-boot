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
 * 权限资源关系表
 */

@Data
@Table(name = "SYS_AUTHORITY_RESOURCE_REL")
public class SysAuthorityResourceRelation extends BaseDTO {
	@Id
	@GeneratedValue
	private Long relationId;

	/**
	 * 权限编码
	 */
	@Length(max = 100)
	private String authorityCode;

	/**
	 * 资源id
	 */
	private Long resourceId;

	/**
	 * 启用标志('Y','N')
	 */
	@ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = IfOrNotHandler.class)
	private String enabledFlag;

}
