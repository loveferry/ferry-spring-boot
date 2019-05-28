package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.system.service.BaseService;

public interface SysUserService extends BaseService<SysUser> {
    /**
     * 根据用户代码查询用户信息
     * @param userCode 用户代码
     * @return 用户个人信息
     */
    SysUser queryByUserCode(String userCode);

    /**
     * 根据用户代码查询用户信息
     * @param userNameZh 用户姓名(中文)
     * @return 用户个人信息
     */
    SysUser queryByUserNameZh(String userNameZh);


}
