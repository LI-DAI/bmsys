package com.ld.bmsys.auth.service.test;

import cn.hutool.core.convert.Convert;
import com.ld.bmsys.auth.service.security.vo.OnlineUser;
import com.ld.bmsys.auth.service.utils.RedisUtil;
import com.ld.bmsys.auth.service.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author ld
 * @Date 2020/3/7 19:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUtil redisUtil;

    public static void main(String[] args) {
        String s = "55${tests}测hi发顺丰圣诞节快乐";
        System.out.println(s.replaceAll("\\$\\{.*\\}", "\n"));
    }

    @Test
    public void test1() {
        System.out.println(SpringContextUtil.getProperties("spring.jackson.time-zone", null, String.class));
    }

    @Test
    public void test2() {
        OnlineUser xiaoming = new OnlineUser(1, "xiaoming", "1.1.1.1", null);
        OnlineUser xiaoli = new OnlineUser(1, "小李", "1.1.1.2", null);
        ArrayList<OnlineUser> users = new ArrayList<>();
        users.add(xiaoli);
        users.add(xiaoming);
        redisUtil.set("users", users);
        List<OnlineUser> users1 = Convert.toList(OnlineUser.class, redisUtil.get("users"));
        System.out.println(users1);
    }

    @Test
    public void test3() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("test", "fsrewqre");
//        map.put("age", 20);
//        redisUtil.set("map", map);
//        Object o = redisUtil.get("test");
//        System.out.println(o.toString());
//        List<SimpleGrantedAuthority> objects = Convert.toList(SimpleGrantedAuthority.class, o);
//        System.out.println(objects);
        List<String> lidai = Stream.of(null, "lidai").collect(Collectors.toList());
        System.out.println(lidai);
    }




}
