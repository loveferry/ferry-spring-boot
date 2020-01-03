package cn.org.ferry.core.mapper.base;

import cn.org.ferry.core.mapper.base.delete.BaseDeleteMapper;
import cn.org.ferry.core.mapper.base.insert.BaseInsertMapper;
import cn.org.ferry.core.mapper.base.select.BaseSelectMapper;
import cn.org.ferry.core.mapper.base.update.BaseUpdateMapper;
import cn.org.ferry.mybatis.annotations.RegisterMapper;

/**
 * <p>通用的基础 mybatis 接口
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface BaseMapper<T> extends BaseSelectMapper<T>, BaseInsertMapper<T>, BaseUpdateMapper<T>, BaseDeleteMapper<T> {

}
