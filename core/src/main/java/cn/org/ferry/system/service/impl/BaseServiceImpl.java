package cn.org.ferry.system.service.impl;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.mybatis.BaseMapper;
import cn.org.ferry.system.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);


    @Autowired
    protected BaseMapper<T> mapper;

    @Override
    public int insertSelective(T t) {
        updateBaseField(t);
        return mapper.insertSelective(t);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        updateBaseField(t);
        return mapper.updateByPrimaryKeySelective(t);
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

    private void updateBaseField(T t){
        setBaseField(BaseDTO.CREATEDBY, 10001L, t.getClass(), t);
        setBaseField(BaseDTO.CREATIONDATE, new Date(), t.getClass(), t);
        setBaseField(BaseDTO.LASTUPDATEDBY, 10001L, t.getClass(), t);
        setBaseField(BaseDTO.LASTUPDATEDATE, new Date(), t.getClass(), t);
    }

    private boolean setBaseField(String fieldName, Object value, Class cls, T t) {
        try {
            if(null == cls.getDeclaredMethod(getMethodName(fieldName)).invoke(t)){
                cls.getDeclaredMethod(setMethodName(fieldName), value.getClass()).invoke(t, value);
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

    private String getMethodName(String fieldName){
        String s = String.valueOf(fieldName.charAt(0));
        return "get"+fieldName.replaceFirst(s, s.toUpperCase());
    }

    private String setMethodName(String fieldName){
        String s = String.valueOf(fieldName.charAt(0));
        return "set"+fieldName.replaceFirst(s, s.toUpperCase());
    }
}
