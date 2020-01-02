package cn.org.ferry.system.mapper;

import cn.org.ferry.mybatis.annotations.RegisterMapper;
import cn.org.ferry.system.mapper.base.BaseMapper;

/**
 * <p>通用 mapper 接口层
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface Mapper<T> extends BaseMapper<T> {
}
