package cn.org.ferry.sys.service;

import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.sys.dto.SysResource;

import java.util.List;

/**
 * Generate by code generator
 * 系统资源表 业务接口
 */

public interface SysResourceService extends BaseService<SysResource> {
    /**
     * 查询所有的资源列表
     */
    List<String> queryAllEnabledResource();

    /**
     * 清除缓存
     */
    void expireAllEnabledResource();


    /**
     *  脚本注册
     *  形如：resource_definition(resource_type, resource_path, enabled_flag)
     *  此方法具有原子性，脚本错误通过 {@link IllegalArgumentException} 抛出
     */
    void scriptRegister(String scripts);

    /**
     * 资源定义
     */
    void scriptDefinition(String script);
}
