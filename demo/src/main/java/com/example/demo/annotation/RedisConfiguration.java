package com.example.demo.annotation;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

@Configuration
public class RedisConfiguration {

    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params){
                    sb.append("_" + obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(
            JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, String> template = new RedisTemplate<>();
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
}
