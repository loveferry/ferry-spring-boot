package cn.org.ferry.sys.mapper;

import cn.org.ferry.sys.dto.SysResource;
import cn.org.ferry.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Generate by code generator
 * 系统资源表 mybatis 接口层
 */

public interface SysResourceMapper extends Mapper<SysResource>{
    /**
     * 查询所有的 rest 资源列表
     */
    List<String> queryAllEnabledResourceByType(@Param("type")String type);


}
