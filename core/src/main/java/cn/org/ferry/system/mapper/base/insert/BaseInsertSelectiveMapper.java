package cn.org.ferry.system.mapper.base.insert;

import cn.org.ferry.system.mybatis.annotation.RegisterMapper;
import cn.org.ferry.system.mybatis.providers.BaseInsertProvider;
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
    void insertSelective(T record);
}
