package cn.org.ferry.system.mapper.base.select;

import cn.org.ferry.mybatis.annotations.RegisterMapper;
import cn.org.ferry.mybatis.providers.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>查询通用 mapper
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface BaseSelectMapper<T> extends BaseSelectOneMapper<T>, BaseSelectAllMapper<T>, BaseSelectByPrimaryKey<T>,BaseSelectCountMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    List<T> select(T record);
}
