package com.ld.bmsys.auth.service.test;

import com.ld.bmsys.auth.service.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author ld
 * @Date 2020/3/7 19:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        String s = "55${tests}测hi发顺丰圣诞节快乐";
        System.out.println(s.replaceAll("\\$\\{.*\\}", "\n"));
    }

    @Test
    public void test1() {
        System.out.println(SpringContextUtil.getProperties("spring.jackson.time-zone", null, String.class));
    }
}
