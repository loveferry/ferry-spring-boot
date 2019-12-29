package cn.org.ferry.system.service.impl;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.mapper.Mapper;
import cn.org.ferry.system.service.BaseService;
import cn.org.ferry.system.utils.BeanUtils;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * 通用业务层实现类
 * @param <T> 实体泛型类
 */

public abstract class BaseServiceImpl<T extends BaseDTO> implements BaseService<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    protected Mapper<T> mapper;

    @Override
    public T insertSelective(T record) {
        updateBaseField(record, INSERT);
        mapper.insertSelective(record);
        return record;
    }

    @Override
    public T insert(T record) {
        updateBaseField(record, INSERT);
        mapper.insert(record);
        return record;
    }

    @Override
    public int delete(T record) {
        return mapper.delete(record);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        updateBaseField(record, UPDATE);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        updateBaseField(record, UPDATE);
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public List<T> select() {
        return mapper.selectAll();
    }

    @Override
    public List<T> select(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectAll();
    }

    @Override
    public List<T> select(T record) {
        return mapper.select(record);
    }

    @Override
    public List<T> select(T record, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.select(record);
    }

    @Override
    public int selectCount(T record) {
        return mapper.selectCount(record);
    }

    protected void updateBaseField(T record, String type){
        Date now = new Date();
        if(UPDATE.equals(type)){
            setBaseField(BaseDTO.LAST_UPDATED_BY, 10001L, record.getClass(), record);
            setBaseField(BaseDTO.LAST_UPDATE_DATE, now, record.getClass(), record);
        }else if(INSERT.equals(type)){
            setBaseField(BaseDTO.CREATED_BY, 10001L, record.getClass(), record);
            setBaseField(BaseDTO.CREATION_DATE, now, record.getClass(), record);
        }
    }

    private boolean setBaseField(String fieldName, Object value, Class<?> cls, T record) {
        try {
            if(null == cls.getDeclaredMethod(BeanUtils.getMethodName(fieldName)).invoke(record)){
                cls.getDeclaredMethod(BeanUtils.setMethodName(fieldName), value.getClass()).invoke(record, value);
            }
        }catch (NoSuchMethodException e) {
            if(cls == Object.class){
                logger.error("该实体类未继承 BaseDto 类,不赋值基础字段: {}", fieldName);
                return false;
            }
            setBaseField(fieldName, value, cls.getSuperclass(), record);
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
