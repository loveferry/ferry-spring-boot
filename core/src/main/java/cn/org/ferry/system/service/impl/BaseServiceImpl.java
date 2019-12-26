package cn.org.ferry.system.service.impl;

import cn.org.ferry.system.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Override
    public int insertSelective(T t) {
        return 0;
    }

    @Override
    public int insert(T t) {
        return 0;
    }

    @Override
    public int delete(T t) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return 0;
    }

    @Override
    public T selectByPrimaryKey(Object o) {
        return null;
    }

    @Override
    public List<T> select() {
        return null;
    }

    @Override
    public List<T> select(int page, int pageSize) {
        return null;
    }

    @Override
    public List<T> select(T t) {
        return null;
    }

    @Override
    public List<T> select(T t, int page, int pageSize) {
        return null;
    }

    @Override
    public int selectCount(T t) {
        return 0;
    }

    @Override
    public int selectCount(T t, int page, int pageSize) {
        return 0;
    }
}
