package cn.org.ferry.core.security.jwt;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/31 14:10
 */

@Service("jwtRedisCache")
public class JwtRedisCache implements JwtCache {


    @Override
    @CachePut(value = CACHE_NAME, key = "#userId")
    public JwtPair put(JwtPair jwtPair, String userId) {
        return jwtPair;
    }

    @CacheEvict(value = CACHE_NAME, key = "#userId")
    @Override
    public void expire(String userId) {

    }

    @Cacheable(value = CACHE_NAME, key = "#userId")
    @Override
    public JwtPair get(String userId) {
        return null;
    }
}
