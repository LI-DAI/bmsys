package com.ld.bmsys.auth.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.stream.Stream;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

/**
 * @Author ld
 * @Date 2020/7/20 15:33
 */
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    private static final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    private static final GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer
            = new GenericJackson2JsonRedisSerializer();

    private final LettuceConnectionFactory connectionFactory;

    public RedisConfig(LettuceConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 缓存解析器
     */
    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(cacheManager());
    }

    /**
     * 用于捕获从Cache中进行CRUD时的异常的回调处理器。
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }

    /**
     * 缓存管理器,处理@Cacheable存取数据时的乱码问题
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration cacheConfiguration =
                defaultCacheConfig()
                        .disableCachingNullValues()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer));
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();

    }

    /**
     * 自定义Key生成规则,类名+方法名+参数值
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName()).append("::").append(method.getName());
            if (objects.length > 0) {
                String params = Stream.of(objects).map(String::valueOf).reduce(String::concat).get();
                sb.append(":").append(params);
            }
            return sb.toString();
        };
    }


    /**
     * 解决@Cacheable存取数据时的乱码问题
     *
     * @param factory
     * @return
     */
    /*@Bean
    public CacheManager cacheManager() {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        //解决查询缓存转换异常的问题
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //失效时间
                .entryTtl(Duration.ZERO)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }*/


}
