package com.ld.bmsys.dict.service.event;

import com.ld.bmsys.dict.service.demo.event.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author LD
 * @Date 2021/4/23 14:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventTest {

    @Autowired
    private PersonService personService;

    @Test
    public void test1() {
        personService.register();
    }
}
