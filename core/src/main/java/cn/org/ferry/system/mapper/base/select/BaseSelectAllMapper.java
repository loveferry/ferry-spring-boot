package cn.org.ferry.system.mapper.base.select;

import cn.org.ferry.system.mybatis.annotation.RegisterMapper;
import cn.org.ferry.system.mybatis.providers.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>通用 mapper 查询，查询所有数据
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/17 10:12
 */

@RegisterMapper
public interface BaseSelectAllMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectAll();
}
