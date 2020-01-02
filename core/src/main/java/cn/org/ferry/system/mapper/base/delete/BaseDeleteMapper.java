package cn.org.ferry.system.mapper.base.delete;

import cn.org.ferry.mybatis.annotations.RegisterMapper;
import cn.org.ferry.mybatis.providers.BaseDeleteProvider;
import org.apache.ibatis.annotations.DeleteProvider;

/**
 * 通用mapper 删除
 * 给定的条件值批量删除
 */

@RegisterMapper
public interface BaseDeleteMapper<T> extends BaseDeleteByPrimaryKeyMapper<T>{
    @DeleteProvider(type = BaseDeleteProvider.class, method = "dynamicSQL")
    int delete(T record);

}
