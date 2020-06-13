package cn.org.ferry.core.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * <p>spring cache 配置累
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/05/31 13:53
 */

@Configuration
@EnableCaching
@EnableConfigurationProperties({CacheProperties.class})
public class SpringCacheConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, CacheProperties cacheProperties) {
        CacheProperties.Redis redis = cacheProperties.getRedis();
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        if(Objects.nonNull(redis.getTimeToLive())){
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(redis.getTimeToLive());
        }
        if(Objects.nonNull(redis.getKeyPrefix())){
            redisCacheConfiguration = redisCacheConfiguration.computePrefixWith(name -> redis.getKeyPrefix() + name + ':');
        }
        if(!redis.isCacheNullValues()){
            redisCacheConfiguration.disableCachingNullValues();
        }
        if(!redis.isUseKeyPrefix()){
            redisCacheConfiguration.disableKeyPrefix();
        }
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
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
//        FastJsonRedisSerializer<T> fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        // 序列化类，对象映射设置
        // 设置 value 的转化格式和 key 的转化格式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
