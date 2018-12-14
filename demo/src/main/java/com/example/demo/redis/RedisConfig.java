package com.example.demo.redis;

import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
@Slf4j
public class RedisConfig {

    /**
     * 我们这里使用的是Jedis Client:
     *
     * 关于JedisConnectionFactory连接工厂的创建:
     *  1.(推荐)我们只需要配置redis相关属性,springboot会帮我们创建一个JedisConnectionFactory redisConnectionFactory的bean  //查看JedisConnectionConfiguration源码
     *  2.我们也可以自己创建JedisConnectionFactory
     *
     *
     *
     * 默认创建redisTemplate和stringRedisTemplate两个模板  //查看RedisAutoConfiguration源码
     *  1.redisTemplate
     *      1.默认使用JdkSerializationRedisSerializer进行序列化  //查看RedisTemplate源码
     *      2.string,hash,list,set,sorted set存取均无问题,存入redis时key和value均是乱码,sorted set分数值没有问题
     *      3.要存储对象,则该类必须实现序列化接口,并且对象存入redis时是乱码
     *  2.stringRedisTemplate
     *      1.默认使用RedisSerializer<String>(即StringRedisSerializer)进行序列化(序列化String类型的key和value,非String类型会报错)  //查看StringRedisTemplate源码
     *      2.string,hash,list,set,sorted set存取均无问题,存入redis时无乱码
     *      3.StringRedisTemplate继承了RedisTemplate<String, String>,所以不能存储对象
     *
     *
     *
     *
     *
     * 自定义RedisTemplate注意:
     * 1.RedisTemplate<String, Object>的泛型(目前感觉使用RedisTemplate<String, Object>会好一点):
     *      String: 代表所能存储的key的类型
     *      Object: 代表所能存储的value的类型
     * 2.注入redisTemplate的时候最好加上泛型(不然有警告,强迫症):
     *      @Autowired
     *      private RedisTemplate<String,Object> redisTemplate;
     *
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
     * (这个相比GenericJackson2JsonRedisSerializer好像效率更高一点,推荐)
     * redisTemplate使用StringRedisSerializer进行序列化key,hash key
     *              使用Jackson2JsonRedisSerializer<Object>进行序列化value,hash value(使用jackson2将对象序列化为json)
     *      1.string,hash,list,set,sorted set存取均无问题,存入redis时无乱码
     *      2.存取Object均无问题,强转为User也没问题,其他对象也可以
     *      3.存取对象时该类不需要实现序列化接口
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(stringRedisSerializer);   //key序列化
        template.setValueSerializer(jackson2JsonRedisSerializer); //value序列化
        template.setHashKeySerializer(stringRedisSerializer);   //hash key序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer); //hash value序列化
        return template;
    }



    /**
     * redisTemplate使用StringRedisSerializer进行序列化key,hash key
     *              使用Jackson2JsonRedisSerializer<User>进行序列化value,hash value(使用jackson2将对象序列化为json)
     *      1.string,hash,list,set,sorted set存取均无问题,存入redis时无乱码
     *      2.存取Object均无问题,并且可以转化为User
     *      3.存取对象时该类不需要实现序列化接口
     */
//    @Bean
//    public RedisTemplate<String, User> redisTemplate(
//            RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, User> template = new RedisTemplate<>();
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
     *      4.存取对象时该类不需要实现序列化接口
     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(
//            RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
//                new GenericJackson2JsonRedisSerializer();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(stringRedisSerializer);   //key序列化
//        template.setValueSerializer(genericJackson2JsonRedisSerializer); //value序列化
//        template.setHashKeySerializer(stringRedisSerializer);   //hash key序列化
//        template.setHashValueSerializer(genericJackson2JsonRedisSerializer); //hash value序列化
//        return template;
//    }



    //手动配置JedisConnectionFactory(不推荐)

//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private Integer port;
//    @Value("${spring.redis.database}")
//    private Integer database;
//    @Value("${spring.redis.password}")
//    private String password;
//    @Value("${spring.redis.timeout}")
//    private String timeout;
//
//    @Value("${spring.redis.jedis.pool.max-active}")
//    private Integer maxActive;
//    @Value("${spring.redis.jedis.pool.max-wait}")
//    private String maxWait;
//    @Value("${spring.redis.jedis.pool.max-idle}")
//    private Integer maxIdle;
//    @Value("${spring.redis.jedis.pool.min-idle}")
//    private Integer minIdle;
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setDatabase(database);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
//
//        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder().readTimeout(Duration.ofMillis(Long.parseLong(timeout))).connectTimeout(Duration.ofMillis(Long.parseLong(timeout)));
//        jedisClientConfigurationBuilder.usePooling().poolConfig(jedisPoolConfig);
//
//        return new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfigurationBuilder.build());
//    }
//
//    @Bean
//    JedisPoolConfig jedisPoolConfig(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWait));
//        return jedisPoolConfig;
//    }





}
