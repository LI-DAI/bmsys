package com.ld.bmsys.dict.service.test;

import com.ld.bmsys.auth.api.feign.UserApi;
import com.ld.bmsys.common.config.AsyncTaskProperties;
import com.ld.bmsys.dict.service.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author LD
 * @date 2021/4/22 14:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Autowired
    private ApplicationContext context;

    @Test
    public void test() {
//        Object map = redisTemplate.opsForValue().get("map");
        //存储users使用的是OnlineUser对象,此模块没有所以获取报错
//        Object users = redisTemplate.opsForValue().get("users");
//        System.out.println(users.toString());
//        redisTemplate.delete("users");
    }

    @Test
    public void test2() {
        AsyncTaskProperties properties = context.getBean(AsyncTaskProperties.class);
        UserApi userApi = context.getBean(UserApi.class);
        WebConfig webConfig = context.getBean(WebConfig.class);

        System.out.println(properties.toString());
        System.out.println(userApi.toString());
        System.out.println(webConfig.toString());

    }
}
