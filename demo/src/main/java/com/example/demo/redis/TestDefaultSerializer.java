package com.example.demo.redis;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 测试redisTemplate使用JdkSerializationRedisSerializer进行序列化
 * 测试stringRedisTemplate使用RedisSerializer<String>(即StringRedisSerializer)进行序列化
 */
//@Component
@Slf4j
public class TestDefaultSerializer {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0/3 * * * * ?")
    public void test() {

        //测试存取对象
        User user = new User();
        user.setName("gailun");
        user.setAge(18);
        Student stu = new Student();
        stu.setName("kate");
        stu.setAge(18);
        stu.setGender("female");
        redisTemplate.opsForValue().set("user_one", user);
        redisTemplate.opsForValue().set("stu_one", stu);

        User u = (User) redisTemplate.opsForValue().get("user_one");
        Student s = (Student) redisTemplate.opsForValue().get("stu_one");
        log.info("user_one:" + JSON.toJSONString(u));
        log.info("stu_one:" + JSON.toJSONString(s));

        //测试string,存取无问题,但是通过redisTemplate存进reids之后value和key显示的是乱码
//        log.info(">>>>>>>>>>>>>>>>>>string start...");
//        redisTemplate.opsForValue().set("age", "18");
//        stringRedisTemplate.opsForValue().set("string_age","18");
//
//        Object age = redisTemplate.opsForValue().get("age");
//        String stringAge = stringRedisTemplate.opsForValue().get("string_age");
//        log.info("redisTemplate get age:" + age);
//        log.info("stringRedisTemplate get age:" + stringAge);
//        log.info(">>>>>>>>>>>>>>>>>>string end...");


//        //测试hash,存取无问题,但是通过redisTemplate存进reids之后value和key显示的是乱码
//        log.info(">>>>>>>>>>>>>>>>>>hash start...");
//        redisTemplate.opsForHash().put("school","name","nanchang");
//        stringRedisTemplate.opsForHash().put("string_school","name","nanchang");
//
//        Object school = redisTemplate.opsForHash().get("school","name");
//        Object stringSchool = stringRedisTemplate.opsForHash().get("string_school", "name");
//        log.info("redisTemplate get school:" + school);
//        log.info("stringRedisTemplate get school:" + stringSchool);
//        log.info(">>>>>>>>>>>>>>>>>>hash end...");


//        //测试list,存取无问题,但是通过redisTemplate存进reids之后value和key显示的是乱码
//        log.info(">>>>>>>>>>>>>>>>>>list start...");
//        redisTemplate.opsForList().rightPush("score","18");
//        stringRedisTemplate.opsForList().rightPush("string_score","18");
//
//        List score = redisTemplate.opsForList().range("score",0,-1);
//        List<String> stringScore = stringRedisTemplate.opsForList().range("string_score",0,-1);
//        log.info("redisTemplate get score:" + score);
//        log.info("stringRedisTemplate get score:" + stringScore);
//        log.info(">>>>>>>>>>>>>>>>>>list end...");


//        //测试set,存取无问题,但是通过redisTemplate存进reids之后value和key显示的是乱码
//        log.info(">>>>>>>>>>>>>>>>>>set start...");
//        redisTemplate.opsForSet().add("hobby","lol");
//        stringRedisTemplate.opsForSet().add("string_hobby","lol");
//
//        Set hobby = redisTemplate.opsForSet().members("hobby");
//        Set<String> stringHobby = stringRedisTemplate.opsForSet().members("string_hobby");
//        log.info("redisTemplate get hobby:" + hobby);
//        log.info("stringRedisTemplate get hobby:" + stringHobby);
//        log.info(">>>>>>>>>>>>>>>>>>set end...");


//        //测试sorted set
//        log.info(">>>>>>>>>>>>>>>>>>sorted set start...");
//        redisTemplate.opsForZSet().add("people","zhangsan",1.0);
//        stringRedisTemplate.opsForZSet().add("string_people","zhangsan",1.0);
//
//        Set people = redisTemplate.opsForZSet().range("people",0,-1);
//        Set<String> stringPeople = stringRedisTemplate.opsForZSet().range("string_people",0,-1);
//        log.info("redisTemplate get people:" + people);
//        log.info("stringRedisTemplate get people:" + stringPeople);
//        log.info(">>>>>>>>>>>>>>>>>>sorted set end...");

    }

}
