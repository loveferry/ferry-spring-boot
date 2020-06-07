package cn.org.ferry.sys.service;

import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.SysRole;

import java.util.List;

/**
 * Generate by code generator
 * 系统角色表 业务接口
 */

public interface SysRoleService extends BaseService<SysRole> {
    /**
     * 根据给定的资源路径，获取授权的角色
     */
    List<String> obtainEnabledRolesByPattern(String type, String pattern);

    /**
     * 清除缓存
     */
    void expireEnabledRolesByPattern(String type, String pattern);

    /**
     * 获取所有角色
     */
    List<String> queryAllEnabledRoleCode();
}
