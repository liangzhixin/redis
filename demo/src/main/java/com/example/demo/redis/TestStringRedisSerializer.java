package com.example.demo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 测试redisTemplate使用StringRedisSerializer进行序列化
 */
//@Component
@Slf4j
public class TestStringRedisSerializer {

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/3 * * * * ?")
    public void test(){

//        //测试string,存取无问题,存进reids之后无乱码
//        log.info(">>>>>>>>>>>>>>>>>>string start...");
//        redisTemplate.opsForValue().set("age", "18");
//
//        Object age = redisTemplate.opsForValue().get("age");
//        log.info("redisTemplate get age:" + age);
//        log.info(">>>>>>>>>>>>>>>>>>string end...");



//        //测试hash,存取无问题,存进reids之后无乱码
//        log.info(">>>>>>>>>>>>>>>>>>hash start...");
//        redisTemplate.opsForHash().put("school", "name","nanchang");
//
//        Object school = redisTemplate.opsForHash().get("school","name");
//        log.info("redisTemplate get school:" + school);
//        log.info(">>>>>>>>>>>>>>>>>>hash end...");



//        //测试list,存取无问题,存进reids之后无乱码
//        log.info(">>>>>>>>>>>>>>>>>>list start...");
//        redisTemplate.opsForList().rightPush("score", "18");
//
//        Object score = redisTemplate.opsForList().range("score",0,-1);
//        log.info("redisTemplate get score:" + score);
//        log.info(">>>>>>>>>>>>>>>>>>list end...");



//        //测试set,存取无问题,存进reids之后无乱码
//        log.info(">>>>>>>>>>>>>>>>>>set start...");
//        redisTemplate.opsForSet().add("hobby", "lol");
//
//        Object hobby = redisTemplate.opsForSet().members("hobby");
//        log.info("redisTemplate get hobby:" + hobby);
//        log.info(">>>>>>>>>>>>>>>>>>set end...");



        //测试hash,存取无问题,存进reids之后无乱码
        log.info(">>>>>>>>>>>>>>>>>>hash start...");
        redisTemplate.opsForZSet().add("people", "zhangsan",2.0);

        Object people = redisTemplate.opsForZSet().range("people",0,-1);
        log.info("redisTemplate get people:" + people);
        log.info(">>>>>>>>>>>>>>>>>>hash end...");
    }


}
