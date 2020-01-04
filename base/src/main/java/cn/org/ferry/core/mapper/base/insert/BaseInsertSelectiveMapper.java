package cn.org.ferry.core.mapper.base.insert;

import cn.org.ferry.mybatis.annotations.RegisterMapper;
import cn.org.ferry.mybatis.providers.BaseInsertProvider;
import org.apache.ibatis.annotations.InsertProvider;

/**
 * <p>插入通用 mapper
 *
 * @author ferry ferry_sy@163.com
 */

@RegisterMapper
public interface BaseInsertSelectiveMapper<T> {
    @InsertProvider(
            type = BaseInsertProvider.class,
            method = "dynamicSQL"
    )
    int insertSelective(T record);
}
