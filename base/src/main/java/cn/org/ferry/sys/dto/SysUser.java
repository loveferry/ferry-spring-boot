package cn.org.ferry.sys.dto;

import cn.org.ferry.core.dto.BaseDTO;
import cn.org.ferry.mybatis.enums.IfOrNot;
import cn.org.ferry.mybatis.enums.Sex;
import lombok.Data;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统用户 创建于 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Data
@Table(name = "sys_user")
public class SysUser extends BaseDTO {
    /**
     * primary key
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long userId;

    /**
     * 系统用户编码
     */
    private String userCode;

    /**
     * 系统用户密码
     */
    private String password;

    /**
     * 系统用户名称
     */
    private String userName;

    /**
     * 说明
     */
    private String description;

    /**
     * 系统用户性别
     */
    private Sex userSex;

    /**
     * 系统用户出生日期
     */
    private Date userBirthday;

    /**
     * 系统用户联系邮箱
     */
    private String userEmail;

    /**
     * 系统用户手机号码
     */
    private String userPhone;

    /**
     * 家庭住址
     */
    private String userFamilyAddress;

    /**
     * 启用标识
     */
    private IfOrNot enabledFlag;
}
