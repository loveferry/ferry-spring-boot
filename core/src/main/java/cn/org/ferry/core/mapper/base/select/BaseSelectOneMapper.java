package cn.org.ferry.core.mapper.base.select;

import cn.org.ferry.mybatis.annotations.RegisterMapper;
import cn.org.ferry.mybatis.providers.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * <p>查询单条记录的通用 mapper
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface BaseSelectOneMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    T selectOne(T record);
}
