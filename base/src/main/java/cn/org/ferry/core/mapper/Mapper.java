package cn.org.ferry.core.mapper;

import cn.org.ferry.core.mapper.base.BaseMapper;
import cn.org.ferry.mybatis.annotations.RegisterMapper;

/**
 * <p>通用 mapper 接口层
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface Mapper<T> extends BaseMapper<T> {
}
