package cn.org.ferry.system.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/25
 * @description token认证策略
 */

@Component
public class TokenTactics {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${spring.redis.token-period}")
    private Long tokenPeriod;

    /**
     * 生成token
     * @param id 用户
     * @param secret 私钥
     */
    public String generateToken(String id, String secret){
        Date now = new Date();
        return JWT.create()
                .withIssuedAt(now)  // 发行日期
                .withAudience(id) // 用户
                .withExpiresAt(new Date(now.getTime()+tokenPeriod.intValue()*1000))  // 设置一个失效期
                .sign(Algorithm.HMAC256(secret));  // 设定一个算法,给定私钥通过算法生产token
    }

    /**
     * 将token存入redis中
     */
    public void setTokenToRedisWithPeriodOfValidity(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, 2*tokenPeriod, TimeUnit.SECONDS);
    }

    /**
     * 从redis中获取token
     */
    public String getToken(String key){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }
}
