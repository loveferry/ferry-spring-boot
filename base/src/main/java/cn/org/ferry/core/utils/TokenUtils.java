package cn.org.ferry.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <p>token工具类
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/01/06 18:48
 */

public final class TokenUtils {
    private TokenUtils(){}

    /**
     * 生成token
     * @param id 用户
     * @param secret 私钥
     */
    public static String generateToken(String id, String secret){
        Date now = new Date();
        return JWT.create()
                .withIssuedAt(now)  // 发行日期
                .withAudience(id) // 用户
                .withExpiresAt(new Date(now.getTime()+ getPeriod()*1000))  // 设置一个失效期
                .sign(Algorithm.HMAC256(secret));  // 设定一个算法,给定私钥通过算法生产token
    }

    /**
     * 将token存入redis中
     */
    public static void setTokenToRedisWithPeriodOfValidity(String key, String value){
        ValueOperations<String, String> valueOperations = ConfigUtil.getBean("redisTemplate", RedisTemplate.class).opsForValue();
        valueOperations.set(key, value, 2*getPeriod(), TimeUnit.SECONDS);
    }

    /**
     * 从redis中获取token
     */
    public static String getToken(String key){
        ValueOperations<String, String> valueOperations = ConfigUtil.getBean("redisTemplate", RedisTemplate.class).opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 默认失效时间 1小时
     */
    private static int getPeriod(){
        return Integer.valueOf(Optional.ofNullable(ConfigUtil.getProperty("spring.redis.token-period")).orElse("1800"));
    }

}
