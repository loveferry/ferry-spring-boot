package cn.org.ferry.mybatis.genid;


import cn.org.ferry.mybatis.exceptions.CommonMapperException;
import cn.org.ferry.mybatis.utils.MetaObjectUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ferry
 */
public class GenIdUtil {

    public static final Map<Class<? extends GenId>, GenId> CACHE = new ConcurrentHashMap<Class<? extends GenId>, GenId>();

    public static final ReentrantLock LOCK = new ReentrantLock();

    /**
     * 生成 Id
     */
    public static void genId(Object target, String property, Class<? extends GenId> genClass, String table, String column) throws CommonMapperException {
        try {
            GenId genId;
            if (CACHE.containsKey(genClass)) {
                genId = CACHE.get(genClass);
            } else {
                LOCK.lock();
                try {
                    if (!CACHE.containsKey(genClass)) {
                        CACHE.put(genClass, genClass.newInstance());
                    }
                    genId = CACHE.get(genClass);
                } finally {
                    LOCK.unlock();
                }
            }
            MetaObject metaObject = MetaObjectUtil.forObject(target);
            if (metaObject.getValue(property) == null) {
                Object id = genId.genId(table, column);
                metaObject.setValue(property, id);
            }
        } catch (Exception e) {
            throw new CommonMapperException("生成 ID 失败!", e);
        }
    }

}
