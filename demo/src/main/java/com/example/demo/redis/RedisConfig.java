package com.example.demo.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * redisTemplate
     *      1.默认使用JdkSerializationRedisSerializer进行序列化  //查看RedisTemplate源码
     *      2.string,hash,list,set,sorted set存取均无问题,存入redis时key和value均是乱码,sorted set分数值没有问题
     * stringRedisTemplate
     *      1.默认使用RedisSerializer<String>(即StringRedisSerializer)进行序列化(序列化String类型的key和value,非String类型会报错)  //查看StringRedisTemplate源码
     *      2.string,hash,list,set,sorted set存取均无问题,存入redis时无乱码
     *
     *      redisTemplate和stringRedisTemplate二者默认的序列化器均不能序列化Object
     */



    /**
     * redisTemplate使用StringRedisSerializer进行序列化
     *      1.string,hash,list,set,sorted set存取均无问题,存入redis时无乱码
     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(
//            RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(stringRedisSerializer);   //key序列化
//        template.setValueSerializer(stringRedisSerializer); //value序列化
//        template.setHashKeySerializer(stringRedisSerializer);   //hash key序列化
//        template.setHashValueSerializer(stringRedisSerializer); //hash value序列化
//        return template;
//    }



    /**
     * redisTemplate使用StringRedisSerializer进行序列化key,hash key
     *              使用Jackson2JsonRedisSerializer<Object>进行序列化value,hash value(使用jackson2将对象序列化为json)
     *      1.string,hash,list,set,sorted set存取均无问题,存入redis时无乱码
     *      2.存取Object均无问题,但是强转为User报错
     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(
//            RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
//                new Jackson2JsonRedisSerializer<Object>(Object.class);
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(stringRedisSerializer);   //key序列化
//        template.setValueSerializer(jackson2JsonRedisSerializer); //value序列化
//        template.setHashKeySerializer(stringRedisSerializer);   //hash key序列化
//        template.setHashValueSerializer(jackson2JsonRedisSerializer); //hash value序列化
//        return template;
//    }



    /**
     * redisTemplate使用StringRedisSerializer进行序列化key,hash key
     *              使用Jackson2JsonRedisSerializer<User>进行序列化value,hash value(使用jackson2将对象序列化为json)
     *      1.string,hash,list,set,sorted set存取均无问题,存入redis时无乱码
     *      2.存取Object均无问题,并且可以转化为User
     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(
//            RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer<User> jackson2JsonRedisSerializer =
//                new Jackson2JsonRedisSerializer<>(User.class);
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(stringRedisSerializer);   //key序列化
//        template.setValueSerializer(jackson2JsonRedisSerializer); //value序列化
//        template.setHashKeySerializer(stringRedisSerializer);   //hash key序列化
//        template.setHashValueSerializer(jackson2JsonRedisSerializer); //hash value序列化
//        return template;
//    }



    /**(目前来看这个比较好用,推荐使用)
     * redisTemplate使用StringRedisSerializer进行序列化key,hash key
     *              使用GenericJackson2JsonRedisSerializer进行序列化value,hash value(使用jackson2将对象序列化为json)
     *      1.string,hash,list,set,sorted set存取均无问题,存入redis时无乱码
     *      2.存取Object均无问题,并且可以转化为User以及其他对象类型
     *      3.序列化后的内容存储了对象的Class信息,可以操作多个对象的反序列化,不止是User
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(
            RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, String> template = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(stringRedisSerializer);   //key序列化
        template.setValueSerializer(genericJackson2JsonRedisSerializer); //value序列化
        template.setHashKeySerializer(stringRedisSerializer);   //hash key序列化
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer); //hash value序列化
        return template;
    }

}
