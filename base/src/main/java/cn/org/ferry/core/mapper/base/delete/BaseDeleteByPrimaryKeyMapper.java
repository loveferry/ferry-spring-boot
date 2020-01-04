package cn.org.ferry.core.mapper.base.delete;


import cn.org.ferry.mybatis.annotations.RegisterMapper;
import cn.org.ferry.mybatis.providers.BaseDeleteProvider;
import org.apache.ibatis.annotations.DeleteProvider;

/**
 * 通用mapper 删除
 * 根据主键删除记录
 */

@RegisterMapper
public interface BaseDeleteByPrimaryKeyMapper<T> {
    @DeleteProvider(type = BaseDeleteProvider.class, method = "dynamicSQL")
    int deleteByPrimaryKey(Object key);

}
