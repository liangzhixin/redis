package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Teacher;
import com.example.demo.mapper.TeacherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Cacheable(value = "tea",keyGenerator = "keyGenerator")
    @Scheduled(cron = "0/3 * * * * ?")
    public Teacher test(){
        long start = System.currentTimeMillis();
        Teacher teacher = teacherMapper.selectById(1);
        log.info(">>>>>>>>" + JSON.toJSONString(teacher));
        log.info(">>>>>>>>耗时:" + (System.currentTimeMillis() - start) + "ms");
        return teacher;
    }
}
