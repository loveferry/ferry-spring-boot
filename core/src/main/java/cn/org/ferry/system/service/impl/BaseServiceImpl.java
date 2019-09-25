package cn.org.ferry.system.service.impl;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.mybatis.BaseMapper;
import cn.org.ferry.system.service.BaseService;
import cn.org.ferry.system.utils.BeanUtils;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);


    @Autowired
    protected BaseMapper<T> mapper;

    @Override
    public int insertSelective(T t) {
        updateBaseField(t, INSERT);
        return mapper.insertSelective(t);
    }

    @Override
    public int insert(T t) {
        updateBaseField(t, INSERT);
        return mapper.insert(t);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        updateBaseField(t, UPDATE);
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        updateBaseField(t, UPDATE);
        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public T selectByPrimaryKey(Object o) {
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public List<T> select() {
        return mapper.selectAll();
    }

    @Override
    public List<T> select(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return select();
    }

    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    @Override
    public List<T> select(T t, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return select(t);
    }

    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public int selectCount(T t, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return selectCount(t);
    }

    private void updateBaseField(T t, String type){
        Date now = new Date();
        if(UPDATE.equals(type)){
            setBaseField(BaseDTO.LASTUPDATEDBY, 10001L, t.getClass(), t);
            setBaseField(BaseDTO.LASTUPDATEDATE, now, t.getClass(), t);
        }else if(INSERT.equals(type)){
            setBaseField(BaseDTO.CREATEDBY, 10001L, t.getClass(), t);
//            setBaseField(BaseDTO.CREATIONDATE, now, t.getClass(), t);
        }
    }

    private boolean setBaseField(String fieldName, Object value, Class cls, T t) {
        try {
            if(null == cls.getDeclaredMethod(BeanUtils.getMethodName(fieldName)).invoke(t)){
                cls.getDeclaredMethod(BeanUtils.setMethodName(fieldName), value.getClass()).invoke(t, value);
            }
        }catch (NoSuchMethodException e) {
            if(cls == Object.class){
                logger.error("该实体类未继承 BaseDto 类,不赋值基础字段: {}", fieldName);
                return false;
            }
            setBaseField(fieldName, value, cls.getSuperclass(), t);
        } catch (IllegalAccessException e) {
            logger.error("不能访问字段 {} 的 getter/setter 方法", fieldName);
            return false;
        } catch (InvocationTargetException e) {
            logger.error("调用字段 {} 的 getter/setter 方法失败", fieldName);
            return false;
        }
        return true;
    }
}
