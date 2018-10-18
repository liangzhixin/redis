package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Teacher;
import com.example.demo.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
@Slf4j
public class Test {

    @Autowired
    private TeacherService teacherService;

    @org.junit.Test
    public void test(){
        Long start = System.currentTimeMillis();
        Teacher teacher = teacherService.getTeacher();
        log.info("teacher:" + JSON.toJSONString(teacher));
        log.info(">>>>>耗时:" + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        teacher = teacherService.getTeacher();
        log.info("teacher:" + JSON.toJSONString(teacher));
        log.info(">>>>>耗时:" + (System.currentTimeMillis() - start) + "ms");
    }
}
