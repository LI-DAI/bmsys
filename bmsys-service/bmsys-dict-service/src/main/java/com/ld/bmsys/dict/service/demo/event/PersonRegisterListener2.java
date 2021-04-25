package com.ld.bmsys.dict.service.demo.event;

import com.ld.bmsys.dict.service.demo.entity.Person;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author LD
 * @date 2021/4/23 14:55
 */
@Component
public class PersonRegisterListener2 {


    @EventListener
    public void sendEmail(PersonRegisterEvent event) {
        Person person = event.getPerson();

        System.out.println(String.format("@EventListener 用户注册成功,发送邮件: %s", person.getName()));
    }
}
