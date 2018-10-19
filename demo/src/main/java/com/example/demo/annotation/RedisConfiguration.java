package com.example.demo.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;

@Configuration
@EnableCaching
public class RedisConfiguration {

    /**
     * 自定义key的生成策略
     */
    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params){
                    sb.append("_").append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 关于RedisCacheManager缓存管理器的创建(配合注解一起使用):
     *  1.我们只需要配置redis相关属性,springboot会帮我们创建一个RedisCacheManager cacheManager的bean  //查看RedisCacheConfiguration源码
     *  2.我们也可以自己创建RedisCacheManager
     */

    @Value("${spring.cache.redis.time-to-live}")
    private String timeToLive;
    @Value("${spring.application.name}")
    private String applicationName;
//    @Value("${spring.cache.redis.key-prefix}")
//    private String keyPrefix;

    //不初始化cacheNames
//    @Bean
//    public RedisCacheManager redisCacheManager(JedisConnectionFactory jedisConnectionFactory){
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(Long.parseLong(timeToLive)))  //设置过期时间,对应属性spring.cache.redis.time-to-live
//                .disableCachingNullValues()  //不缓存空值,对应属性spring.cache.redis.cache-null-values
////                .prefixKeysWith(keyPrefix)  //效果自己测试,对应属性spring.cache.redis.key-prefix,应该不怎么使用
//                .computePrefixWith(cacheName -> applicationName.concat(":").concat(cacheName).concat(":"))  ////设置key的前缀
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))  //设置key的序列化方式
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));  //设置value的序列化方式
//
//        return RedisCacheManager.builder(jedisConnectionFactory)
//                .cacheDefaults(config)
//                .build();
//    }



    //初始化cacheNames
    @Bean
    public RedisCacheManager redisCacheManager(JedisConnectionFactory jedisConnectionFactory){
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(Long.parseLong(timeToLive)))  //设置过期时间
                .disableCachingNullValues()  //不缓存空值
                .computePrefixWith(cacheName -> applicationName.concat(":").concat(cacheName).concat(":"))  ////设置key的前缀
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))  //设置key的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));  //设置value的序列化方式

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("user");
        cacheNames.add("stu");
        cacheNames.add("tea");

        Map<String,RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("user", config);
        configMap.put("stu", config.entryTtl(Duration.ofMillis(-1)));
        configMap.put("tea", config.entryTtl(Duration.ofMillis(-1)));

        return RedisCacheManager.builder(jedisConnectionFactory)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(stringRedisSerializer);   //key序列化
        template.setValueSerializer(genericJackson2JsonRedisSerializer); //value序列化
        template.setHashKeySerializer(stringRedisSerializer);   //hash key序列化
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer); //hash value序列化
        return template;
    }

    //手动配置JedisConnectionFactory

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.database}")
    private Integer database;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private String timeout;

    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private String maxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder().readTimeout(Duration.ofMillis(Long.parseLong(timeout))).connectTimeout(Duration.ofMillis(Long.parseLong(timeout)));
        jedisClientConfigurationBuilder.usePooling().poolConfig(jedisPoolConfig);

        return new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfigurationBuilder.build());
    }

    @Bean
    JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWait));
        return jedisPoolConfig;
    }
}
