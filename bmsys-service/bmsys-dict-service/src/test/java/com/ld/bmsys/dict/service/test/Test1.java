package com.ld.bmsys.dict.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author LD
 * @Date 2021/4/22 14:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test(){
        Object map = redisTemplate.opsForValue().get("map");
        //存储users使用的是OnlineUser对象,此模块没有所以获取报错
//        Object users = redisTemplate.opsForValue().get("users");
//        System.out.println(users.toString());
        redisTemplate.delete("users");
    }
}
