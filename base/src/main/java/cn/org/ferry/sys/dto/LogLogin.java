package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Generate by code generator
 * 登陆日志表
 */

@Data
@Table(name = "log_login")
public class LogLogin extends BaseDTO {
	@Id
	@GeneratedValue
	private Long logId;

	/**
	 * 用户代码
	 */
	@Length(max = 100)
	private String userCode;

	/**
	 * 登陆时间
	 */
	private Date loginDate;

	/**
	 * 登出时间
	 */
	private Date logoutDate;

	/**
	 * 登陆地址
	 */
	@Length(max = 100)
	private String ip;

	/**
	 * 代理
	 */
	@Length(max = 1000)
	private String userAgent;
}
