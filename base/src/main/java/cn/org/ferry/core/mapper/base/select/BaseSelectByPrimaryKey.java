package cn.org.ferry.core.mapper.base.select;

import cn.org.ferry.mybatis.annotations.RegisterMapper;
import cn.org.ferry.mybatis.providers.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * <p>通用mapper 根据主键查找记录
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface BaseSelectByPrimaryKey<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    T selectByPrimaryKey(Object key);
}
