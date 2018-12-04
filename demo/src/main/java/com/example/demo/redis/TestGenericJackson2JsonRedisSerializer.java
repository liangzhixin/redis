package com.example.demo.redis;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 测试redisTemplate使用GenericJackson2JsonRedisSerializer进行序列化
 */
//@Component
@Slf4j
public class TestGenericJackson2JsonRedisSerializer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0/3 * * * * ?")
    public void test() {
        User user = new User();
        user.setName("zhangsan");
        user.setAge(18);

        Student stu = new Student();
        stu.setName("lisi");
        stu.setAge(20);
        stu.setGender("male");

//        //测试string,存取无问题,存进reids之后以json格式字符串存储Object(包括对象的Class信息),转化为User或Stu以及其他对象类型也没问题
//        log.info(">>>>>>>>>>>>>>>>>>string start...");
//
//        redisTemplate.opsForValue().set("obj_user", user);
//        Object obj = redisTemplate.opsForValue().get("obj_user");
//        log.info("string get obj_user:" + JSON.toJSONString(obj));
//        user = (User) redisTemplate.opsForValue().get("obj_user");
//        log.info("string get obj_user:" + JSON.toJSONString(user));
//
//        redisTemplate.opsForValue().set("obj_stu", stu);
//        obj = redisTemplate.opsForValue().get("obj_stu");
//        log.info("string get obj_stu:" + JSON.toJSONString(obj));
//        stu = (Student) redisTemplate.opsForValue().get("obj_stu");
//        log.info("string get obj_stu:" + JSON.toJSONString(stu));
//
//        log.info(">>>>>>>>>>>>>>>>>>string end...");


//        //测试hash,存取无问题,存进reids之后以json格式字符串存储Object(包括对象的Class信息),转化为User或Stu以及其他对象类型也没问题
//        log.info(">>>>>>>>>>>>>>>>>>hash start...");
//
//        redisTemplate.opsForHash().put("obj_user_hash","user1", user);
//        Object obj = redisTemplate.opsForHash().get("obj_user_hash","user1");
//        log.info("string get hash:" + JSON.toJSONString(obj));
//        User u = (User) redisTemplate.opsForHash().get("obj_user_hash","user1");
//        log.info("string get hash:" + JSON.toJSONString(u));
//
//        redisTemplate.opsForHash().put("obj_stu_hash","stu1", stu);
//        obj = redisTemplate.opsForHash().get("obj_stu_hash","stu1");
//        log.info("string get hash:" + JSON.toJSONString(obj));
//        stu = (Student) redisTemplate.opsForHash().get("obj_stu_hash","stu1");
//        log.info("string get hash:" + JSON.toJSONString(stu));
//        log.info(">>>>>>>>>>>>>>>>>>hash end...");


//        //测试list,存取无问题,存进reids之后以json格式字符串存储Object(包括对象的Class信息),转化为User或Stu以及其他对象类型也没问题
//        log.info(">>>>>>>>>>>>>>>>>>list start...");
//
//        redisTemplate.opsForList().rightPush("obj_user_list",user);
//        List list = redisTemplate.opsForList().range("obj_user_list",0,-1);
//        log.info("string get list:" + list);
//        List<User> userlist = redisTemplate.opsForList().range("obj_user_list",0,-1);
//        for(User u: userlist) {
//            log.info("u:" + JSON.toJSONString(u));
//        }
//        log.info("string get list:" + userlist);
//
//        redisTemplate.opsForList().rightPush("obj_stu_list",stu);
//        list = redisTemplate.opsForList().range("obj_stu_list",0,-1);
//        log.info("string get list:" + list);
//        List<Student> stulist = redisTemplate.opsForList().range("obj_stu_list",0,-1);
//        for(Student s: stulist) {
//            log.info("s:" + JSON.toJSONString(s));
//        }
//        log.info("string get list:" + stulist);
//
//        log.info(">>>>>>>>>>>>>>>>>>list end...");


//        //测试set,存取无问题,存进reids之后以json格式字符串存储Object(包括对象的Class信息),转化为User或Stu以及其他对象类型也没问题
//        log.info(">>>>>>>>>>>>>>>>>>set start...");
//
//        redisTemplate.opsForSet().add("obj_user_set",user);
//        Set set = redisTemplate.opsForSet().members("obj_user_set");
//        log.info("string get set:" + set);
//        Set<User> userSet = redisTemplate.opsForSet().members("obj_user_set");
//        for (User u : userSet) {
//            log.info("u:" + JSON.toJSONString(u));
//        }
//
//        redisTemplate.opsForSet().add("obj_stu_set",stu);
//        set = redisTemplate.opsForSet().members("obj_stu_set");
//        log.info("string get set:" + set);
//        Set<Student> stuSet = redisTemplate.opsForSet().members("obj_stu_set");
//        for (Student s : stuSet) {
//            log.info("s:" + JSON.toJSONString(s));
//        }
//
//        log.info(">>>>>>>>>>>>>>>>>>set end...");


//        //测试sorted set,存取无问题,存进reids之后以json格式字符串存储Object(包括对象的Class信息),转化为User或Stu以及其他对象类型也没问题
//        log.info(">>>>>>>>>>>>>>>>>>sorted set start...");
//
//        redisTemplate.opsForZSet().add("obj_user_sorted_set",user,3.0);
//        Set set = redisTemplate.opsForZSet().range("obj_user_sorted_set",0,-1);
//        log.info("string get sorted set:" + set);
//        Set<User> userSet = redisTemplate.opsForZSet().range("obj_user_sorted_set",0,-1);
//        for (User u : userSet) {
//            log.info("u:" + JSON.toJSONString(u));
//        }
//
//        redisTemplate.opsForZSet().add("obj_stu_sorted_set",stu,3.0);
//        set = redisTemplate.opsForZSet().range("obj_stu_sorted_set",0,-1);
//        log.info("string get sorted set:" + set);
//        Set<Student> stuSet = redisTemplate.opsForZSet().range("obj_stu_sorted_set",0,-1);
//        for (Student s : stuSet) {
//            log.info("s:" + JSON.toJSONString(s));
//        }
//        log.info(">>>>>>>>>>>>>>>>>>sorted set end...");
    }
}
