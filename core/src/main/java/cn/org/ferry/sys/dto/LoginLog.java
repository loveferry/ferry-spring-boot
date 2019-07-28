package cn.org.ferry.sys.dto;

import cn.org.ferry.system.dto.BaseDTO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 登陆日志 创建于 2019-07-23
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@Table(name = "login_log")
public class LoginLog extends BaseDTO {
    /**
     * primary key
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long loginLogId;

    /**
     * 用户编码
     */
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
    private String ip;

    /**
     * 代理
     */
    private String userAgent;
}
