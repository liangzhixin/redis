package com.example.demo.redis;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 测试redisTemplate使用Jackson2JsonRedisSerializer<Object>进行序列化
 */
@Component
@Slf4j
public class TestJackson2JsonRedisSerializer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0/3 * * * * ?")
    public void test(){

        User user = new User();
        user.setName("zhangsan");
        user.setAge(18);

        //测试string,存取无问题,存进reids之后以json格式字符串存储Object,但是强转为User时会报错
        log.info(">>>>>>>>>>>>>>>>>>string start...");
        redisTemplate.opsForValue().set("obj_user",user);

        Object obj = redisTemplate.opsForValue().get("obj_user");
        log.info("string get obj_user:" + JSON.toJSONString(obj));
        //下面会报错（加上那段代码又不会报错了）
        user = (User) redisTemplate.opsForValue().get("obj_user");
        log.info("string get obj_user:" + JSON.toJSONString(user));
        log.info(">>>>>>>>>>>>>>>>>>string end...");



//        //测试hash,存取无问题,存进reids之后以json格式字符串存储Object,但是强转为User时会报错
//        log.info(">>>>>>>>>>>>>>>>>>hash start...");
//        redisTemplate.opsForHash().put("obj_user_hash","user1", user);
//
//        Object obj = redisTemplate.opsForHash().get("obj_user_hash","user1");
//        log.info("string get hash:" + obj);
//
//        //下面会报错
//        User u = (User) redisTemplate.opsForHash().get("obj_user_hash","user1");
//        log.info("string get hash:" + JSON.toJSONString(u));
//        log.info(">>>>>>>>>>>>>>>>>>hash end...");



//        //测试list,存取无问题,存进reids之后以json格式字符串存储Object,但是强转为User时会报错
//        log.info(">>>>>>>>>>>>>>>>>>list start...");
//        redisTemplate.opsForList().rightPush("obj_user_list",user);
//
//        List list = redisTemplate.opsForList().range("obj_user_list",0,-1);
//        log.info("string get list:" + list);
//
//        //下面会报错
//        List<User> userlist = redisTemplate.opsForList().range("obj_user_list",0,-1);
//        for(User u: userlist) {
//            log.info("u:" + JSON.toJSONString(u));
//        }
//        log.info("string get list:" + userlist);
//        log.info(">>>>>>>>>>>>>>>>>>list end...");



//        //测试set,存取无问题,存进reids之后以json格式字符串存储Object,但是强转为User时会报错
//        log.info(">>>>>>>>>>>>>>>>>>set start...");
//        redisTemplate.opsForSet().add("obj_user_set",user);
//
//        Set set = redisTemplate.opsForSet().members("obj_user_set");
//        log.info("string get set:" + set);
//
//        //下面会报错
//        Set<User> userSet = redisTemplate.opsForSet().members("obj_user_set");
//        for (User u : userSet) {
//            log.info("u:" + JSON.toJSONString(u));
//        }
//        log.info(">>>>>>>>>>>>>>>>>>set end...");



//        //测试sorted set,存取无问题,存进reids之后以json格式字符串存储Object,但是强转为User时会报错
//        log.info(">>>>>>>>>>>>>>>>>>sorted set start...");
//        redisTemplate.opsForZSet().add("obj_user_sorted_set",user,3.0);
//
//        Set set = redisTemplate.opsForZSet().range("obj_user_sorted_set",0,-1);
//        log.info("string get sorted set:" + set);
//
//        //下面会报错
//        Set<User> userSet = redisTemplate.opsForZSet().range("obj_user_sorted_set",0,-1);
//        for (User u : userSet) {
//            log.info("u:" + JSON.toJSONString(u));
//        }
//        log.info(">>>>>>>>>>>>>>>>>>sorted set end...");

    }
}
