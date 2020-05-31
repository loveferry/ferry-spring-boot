package cn.org.ferry.core.configurations;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>spring cache 配置累
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/31 13:53
 */

@Configuration
@EnableCaching
public class SpringCacheConfiguration {

    private Environment environment;

    public SpringCacheConfiguration(Environment environment){
        this.environment = environment;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.create(redisConnectionFactory);
    }

    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        String clientName = environment.getProperty("spring.redis.client-name");
        if (null != clientName) {
            redisConnectionFactory.getConnection().setClientName(clientName.getBytes());
        }
        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        ObjectMapper om = new ObjectMapper();
//        // 设置可见度
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 启动默认的类型
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 序列化类
        FastJsonRedisSerializer<T> fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

        // 序列化类，对象映射设置
        // 设置 value 的转化格式和 key 的转化格式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
