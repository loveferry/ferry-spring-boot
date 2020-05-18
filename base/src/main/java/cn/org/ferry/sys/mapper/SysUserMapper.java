package cn.org.ferry.sys.mapper;

import cn.org.ferry.core.mapper.Mapper;
import cn.org.ferry.sys.dto.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends Mapper<SysUser> {
    /**
     * 查询员工信息
     * @param userName 员工名称
     * @return 返回员工信息
     */
    SysUser queryByUserNameWithEnabled(@Param("userName") String userName);


}
