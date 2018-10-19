package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Teacher;
import com.example.demo.mapper.TeacherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@CacheConfig(cacheNames = "tea",keyGenerator = "keyGenerator")
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * @Cacheable注解:
     *      如果缓存没有值,从则执行方法并缓存数据(返回值),如果缓存有值,则从缓存中获取值
     *      判断缓存中有没有值: 其实是判断key在不在缓存中,在的话根据key取值,不在的话缓存数据
     *  1.value: 等价于cacheNames
     *  2.cacheNames: 指定缓存的名称
     *  3.key: 使用spring El表达式定义key,不指定会使用全部的参数计算一个key值,与keyGenerator不能同时使用
     *  4.keyGenerator: 定义key生成的类,与key不能同时使用
     *  5.sync:
     *    sync=true: a. 如果缓存中没有数据,多个线程同时访问这个方法,则只有一个方法会执行到方法,其它方法需要等待
     *             b. 如果缓存中已经有数据,则多个线程可以同时从缓存中获取数据
     *    sync=false(默认): 跟true相反,不会加锁
     *  6.condition: 在执行方法前，condition的值为true，则缓存数据
     *  7.unless: 在执行方法后，condition的值为true，则不缓存数据
     *  ...
     *
     *
     *
     * @CachePut注解: 每次执行都会执行方法,无论缓存里是否有值,同时使用新的返回值的替换缓存中的值
     *  属性类似于@Cacheable注解
     *
     *
     *
     * @CacheEvict注解: 删除缓存(如果方法执行抛出异常，则不会清空缓存)
     *  1.属性类似于@Cacheable注解
     *  2.allEntries:
     *    allEntries=true: 则方法调用后将立即清空所有缓存
     *    allEntries=false(默认): 删除满足key或condition等条件的缓存
     *  3.beforeInvocation:
     *    beforeInvocation=true: 在方法还没有执行的时候就清空缓存
     *    beforeInvocation=false(默认): 方法执行完清空缓存(如果方法执行抛出异常，则不会清空缓存)
     *  ...
     *
     *
     *
     * @CacheConfig注解: 设置类级别的相关属性,在类上使用
     *  1.cacheNames: 类中的缓存操作使用的默认缓存的名称
     *  2.keyGenerator: 设置类中key的生成策略
     *  ...
     */

    @Cacheable
//    @CachePut(value = "tea",keyGenerator = "keyGenerator")
//    @CacheEvict(value = "tea",keyGenerator = "keyGenerator",beforeInvocation = true)
    public Teacher getTeacher(){
//        int i = 1/0;
        log.info("执行了此方法...");
        return teacherMapper.selectById(2);
//        return null;  //如果设置了不缓存null值,这里返回null那么缓存数据时会报错
    }

}
