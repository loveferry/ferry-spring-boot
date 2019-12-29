package cn.org.ferry.system.mapper.base.update;

import cn.org.ferry.system.mybatis.annotation.RegisterMapper;
import cn.org.ferry.system.mybatis.providers.BaseUpdateProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 通用mapper 更新
 * 不更新null
 */

@RegisterMapper
public interface BaseUpdateByPrimaryKeySelectiveMapper<T> {
    @UpdateProvider(type = BaseUpdateProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeySelective(T record);

}