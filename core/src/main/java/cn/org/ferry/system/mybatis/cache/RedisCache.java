package cn.org.ferry.system.mybatis.cache;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * <p>使用redis作为缓存
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/24 14:42
 */

public class RedisCache implements Cache {
    /**
     * 缓存id
     */
    private String id;

    /**
     * 字符串键值对对象
     */
    private ValueOperations<Object, Object> valueOperations;

    /**
     * 列表键值对对象
     */
    private ListOperations<Object, Object> listOperations;

    /**
     * 哈希键值对对象
     */
    private HashOperations<Object, Object, Object> hashOperations;

    /**
     * 集合键值对对象
     */
    private SetOperations<Object, Object> setOperations;

    /**
     * 有序集合键值对对象
     */
    private ZSetOperations<Object, Object> zSetOperations;

    private static RedisTemplate<String, String> redisTemplate;

    public RedisCache(String cacheId){
        if(StringUtils.isEmpty(cacheId)){
            throw new CacheException("Cache instance required a cache id.");
        }
        this.id = cacheId;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {

    }

    @Override
    public Object getObject(Object key) {
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
