package cn.org.ferry.core.security.jwt;

/**
 * <p>jwt 缓存接口类
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/30 14:17
 */

public interface JwtCache {
    String CACHE_NAME = "token";

    /**
     * 缓存jwt
     */
    JwtPair put(JwtPair jwtPair, String userId);


    /**
     * 失效
     */
    void expire(String userId);


    /**
     * 从缓存中获取 jwt
     */
    JwtPair get(String userId);

}
