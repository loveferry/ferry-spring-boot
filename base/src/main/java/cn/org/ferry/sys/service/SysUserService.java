package cn.org.ferry.sys.service;

import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.SysUser;

import java.util.List;

public interface SysUserService extends BaseService<SysUser> {
    /**
     * 查询用户信息
     *  登录成功返回数据
     * @param userName 用户名称
     * @return 用户个人信息
     */
    SysUser queryForLoginSuccess(String userName);

    /**
     * 查询用户信息
     *      - security 认证
     * @param userName 用户名称
     * @return 用户个人信息
     */
    SysUser queryByUserNameForSecurityAuthentication(String userName);

    /**
     * 用户信息查询
     */
    List<SysUser> query(String userName, String description);
}
