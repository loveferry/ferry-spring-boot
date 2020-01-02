package cn.org.ferry.system.mapper.base.select;

import cn.org.ferry.mybatis.annotations.RegisterMapper;
import cn.org.ferry.mybatis.providers.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * <p>通用mapper 查询数量
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface BaseSelectCountMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    int selectCount(T record);
}
