package cn.org.ferry.sys.mapper;

import cn.org.ferry.sys.dto.SysRole;
import cn.org.ferry.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Generate by code generator
 * 系统角色表 mybatis 接口层
 */

public interface SysRoleMapper extends Mapper<SysRole>{
    /**
     * 根据给定的资源路径，获取授权的角色
     */
    List<String> obtainEnabledRolesByPattern(@Param("type") String type, @Param("pattern") String pattern);

    /**
     * 获取所有角色
     */
    List<String> queryAllEnabledRoleCode();
}
