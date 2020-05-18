package cn.org.ferry.sys.service;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.SysUser;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface SysUserService extends BaseService<SysUser> {
    /**
     * 根据用户代码查询用户信息
     * @param userCode 用户代码
     * @return 用户个人信息
     */
    SysUser queryByUserCode(String userCode);

    /**
     * 根据用户名称查询用户信息
     * @param userName 用户名称
     * @return 用户个人信息
     */
    SysUser queryByUserNameWithEnabled(String userName);

    /**
     * 登陆
     */
    ResponseData login(HttpServletRequest request, SysUser sysUser);

    /**
     * 用户信息查询
     */
    List<SysUser> query(String userName, String description);
}
