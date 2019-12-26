package cn.org.ferry.system.mapper.base;

import cn.org.ferry.system.mapper.base.select.BaseSelectMapper;
import cn.org.ferry.system.mybatis.annotation.RegisterMapper;

/**
 * <p>通用的基础 mybatis 接口
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface BaseMapper<T> extends BaseSelectMapper<T> {

}
