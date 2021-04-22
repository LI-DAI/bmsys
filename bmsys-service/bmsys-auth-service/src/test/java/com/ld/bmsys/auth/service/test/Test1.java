package com.ld.bmsys.auth.service.test;

import cn.hutool.core.convert.Convert;
import com.ld.bmsys.auth.api.entity.Menu;
import com.ld.bmsys.auth.service.security.vo.OnlineUser;
import com.ld.bmsys.auth.service.utils.RedisUtil;
import com.ld.bmsys.auth.service.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        OnlineUser xiaoming = new OnlineUser(1, "xiaoming", "1.1.1.1", LocalDateTime.now());
        OnlineUser xiaoli = new OnlineUser(1, "小李", "1.1.1.2", LocalDateTime.now());
        ArrayList<OnlineUser> users = new ArrayList<>();
        users.add(xiaoli);
        users.add(xiaoming);
        redisUtil.set("users", users);
        List<OnlineUser> users1 = (List<OnlineUser>) redisUtil.get("users");
        System.out.println(users1);
    }

    @Test
    public void test3() {
        Object o = redisUtil.get("Menu::all");
        System.out.println(o.toString());
        List<Menu> objects = Convert.toList(Menu.class, o);
        System.out.println(objects);
    }

}
