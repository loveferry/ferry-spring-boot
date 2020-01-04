package cn.org.ferry.core.service;

import cn.org.ferry.core.dto.BaseDTO;

import java.util.List;

/**
 * 通用业务层
 * @param <T> 实体泛型类
 */

public interface BaseService<T extends BaseDTO>{
    /**
     * 更新
     */
    String UPDATE = "UPDATE";

    /**
     * 新增
     */
    String INSERT = "INSERT";

    T insertSelective(T record);

    T insert(T record);

    int delete(T record);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    T selectByPrimaryKey(Object key);

    List<T> select();

    List<T> select(int page, int pageSize);

    List<T> select(T record);

    List<T> select(T record, int page, int pageSize);

    int selectCount(T record);
}
