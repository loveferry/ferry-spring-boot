package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Generate by code generator
 * 登陆日志表
 */

@Table(name = "log_login")
public class LogLogin extends BaseDTO {
	@Id
	@GeneratedValue(generator = "JDBC")
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

	public void setLogId(Long logId){
		this.logId = logId;
	}

	public Long getLogId(){
		return this.logId;
	}

	public void setUserCode(String userCode){
		this.userCode = userCode;
	}

	public String getUserCode(){
		return this.userCode;
	}

	public void setLoginDate(Date loginDate){
		this.loginDate = loginDate;
	}

	public Date getLoginDate(){
		return this.loginDate;
	}

	public void setLogoutDate(Date logoutDate){
		this.logoutDate = logoutDate;
	}

	public Date getLogoutDate(){
		return this.logoutDate;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
	}

	public void setUserAgent(String userAgent){
		this.userAgent = userAgent;
	}

	public String getUserAgent(){
		return this.userAgent;
	}

}
