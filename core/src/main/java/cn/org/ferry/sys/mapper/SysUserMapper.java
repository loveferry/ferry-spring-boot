package cn.org.ferry.sys.mapper;

import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.system.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser queryByUserCode(@Param("userCode") String userCode);

    SysUser queryByUserNameZh(@Param("userNameZh") String userNameZh);
}
