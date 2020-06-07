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
    List<String> queryAllEnabledResourceByType(String type);

    /**
     * 清除缓存
     */
    void expireAllEnabledResourceByType(String type);


}
