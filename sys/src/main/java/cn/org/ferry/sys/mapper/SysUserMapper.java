package cn.org.ferry.sys.mapper;

import cn.org.ferry.core.mapper.Mapper;
import cn.org.ferry.sys.dto.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends Mapper<SysUser> {
    /**
     * 查询员工信息
     * @param userCode 员工代码
     * @return 返回员工信息
     */
    SysUser queryByUserCode(@Param("userCode") String userCode);
}
