package cn.org.ferry.sys.mapper;

import cn.org.ferry.core.mapper.Mapper;
import cn.org.ferry.sys.dto.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends Mapper<SysUser> {
    /**
     * 查询用户信息
     *      - security 认证
     * @param userName 用户名称
     * @return 用户个人信息
     */
    SysUser queryByUserNameForSecurityAuthentication(@Param("userName") String userName);


}
