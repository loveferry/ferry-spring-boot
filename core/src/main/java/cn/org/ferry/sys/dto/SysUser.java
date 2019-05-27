package cn.org.ferry.sys.dto;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.mybatis.handler.SexHandler;
import cn.org.ferry.system.sysenum.Sex;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
     * 系统用户名称(英文)
     */
    private String userNameEn;

    /**
     * 系统用户名称(简体中文)
     */
    private String userNameZh;

    /**
     * 系统用户性别
     */
    @ColumnType(typeHandler = SexHandler.class)
    private Sex userSex;

    /**
     * 系统用户出生日期
     */
    private Date userBirthdate;

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
}
