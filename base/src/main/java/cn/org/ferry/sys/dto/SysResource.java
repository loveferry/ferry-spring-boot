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
 * 系统资源表
 */

@Data
@Table(name = "SYS_RESOURCE")
public class SysResource extends BaseDTO {
	public static final String RESOURCE_TYPE_REST = "rest";

	@Id
	@GeneratedValue
	private Long resourceId;

	/**
	 * 资源类型
	 */
	@Length(max = 10)
	private String resourceType;

	/**
	 * 资源路径
	 */
	@Length(max = 200)
	private String resourcePath;

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
