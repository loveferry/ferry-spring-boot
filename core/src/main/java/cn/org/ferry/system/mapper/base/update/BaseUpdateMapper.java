package cn.org.ferry.system.mapper.base.update;

import cn.org.ferry.mybatis.annotations.RegisterMapper;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/27 09:57
 */

@RegisterMapper
public interface BaseUpdateMapper<T> extends BaseUpdateByPrimaryKeyMapper<T>, BaseUpdateByPrimaryKeySelectiveMapper<T>{
}
