package cn.org.ferry.system.mapper.base.select;

import cn.org.ferry.system.mybatis.annotation.RegisterMapper;

/**
 * <p>查询通用 mapper
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface BaseSelectMapper<T> extends BaseSelectOneMapper<T>, BaseSelectAllMapper<T> {
}
