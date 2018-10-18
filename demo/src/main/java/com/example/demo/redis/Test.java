package com.example.demo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class Test {

    @Autowired
    RedisProperties redisProperties;

    @Scheduled(cron = "0/3 * * * * ?")
    public void test(){
        log.info("host:" + redisProperties.getHost());
        log.info("port:" + redisProperties.getPort());
        log.info("password:" + redisProperties.getPassword());
        log.info("database:" + redisProperties.getDatabase());
        log.info("timeout:" + redisProperties.getTimeout());
        log.info("max-active:" + redisProperties.getJedis().getPool().getMaxActive());
        log.info("max-wait:" + redisProperties.getJedis().getPool().getMaxWait());
        log.info("max-idle:" + redisProperties.getJedis().getPool().getMaxIdle());
        log.info("min-idle:" + redisProperties.getJedis().getPool().getMinIdle());
    }
}
