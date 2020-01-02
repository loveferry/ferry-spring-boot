package cn.org.ferry.mybatis.cache;

import cn.org.ferry.mybatis.utils.ConfigUtil;
import cn.org.ferry.mybatis.utils.StringUtil;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>使用redis作为缓存
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/24 14:42
 */

public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    /**
     * 缓存id
     */
    private String id;

    public RedisCache(String cacheId){
        if(StringUtil.isEmpty(cacheId)){
            throw new CacheException("Cache instance required a cache id.");
        }
        this.id = cacheId.replace('.', ':');
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        HashOperations<String, Object, Object> hashOperations = getRedisTemplate().opsForHash();
        hashOperations.put(id, key, value);
        logger.info("Mybatis set second level cache for id : {}", id);
    }

    @Override
    public Object getObject(Object key) {
        logger.info("Mybatis get second level cache for id : {}", id);
        HashOperations<String, Object, Object> hashOperations = getRedisTemplate().opsForHash();
        return hashOperations.get(id, key);
    }

    @Override
    public Object removeObject(Object key) {
        HashOperations<String, Object, Object> hashOperations = getRedisTemplate().opsForHash();
        Object value = hashOperations.get(id, key);
        Long count = hashOperations.delete(id, key);
        logger.info("Mybatis remove second level cache for id : {}, delete count : {}", id, count);
        return value;
    }

    @Override
    public void clear() {
        getRedisTemplate().delete(id);
        logger.info("Mybatis clear second level cache for id : {}", id);
    }

    @Override
    public int getSize() {
        logger.info("Get Mybatis second level cache size for id : {}", id);
        HashOperations<String, Object, Object> hashOperations = getRedisTemplate().opsForHash();
        return Integer.valueOf(hashOperations.size(id).toString());
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return new ReentrantReadWriteLock(true);
    }

    private RedisTemplate<String, Object> getRedisTemplate(){
        return ConfigUtil.getBean("redisTemplate");
    }

}
