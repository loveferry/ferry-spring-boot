package cn.org.ferry.system.service;

import java.util.List;

public interface BaseService<T>{
    /**
     * 更新
     */
    String UPDATE = "UPDATE";

    /**
     * 新增
     */
    String INSERT = "INSERT";

    int insertSelective(T t);

    int insert(T t);

    int delete(T t);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    T selectByPrimaryKey(Object o);

    List<T> select();

    List<T> select(int page, int pageSize);

    List<T> select(T t);

    List<T> select(T t, int page, int pageSize);

    int selectCount(T t);

    int selectCount(T t, int page, int pageSize);
}
