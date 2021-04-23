package com.ld.bmsys.dict.service.demo.event;

import com.ld.bmsys.dict.service.demo.entity.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Author LD
 * @Date 2021/4/23 14:40
 */
@Service
public class PersonService {

    private final ApplicationContext context;

    public PersonService(ApplicationContext context) {
        this.context = context;
    }

    public void register() {

        Person person = new Person("小明", 20);

        System.out.println("person start registration...");

        //这里默认是非异步,需要执行完成才会执行下面的代码
        context.publishEvent(new PersonRegisterEvent(this, person));

        System.out.println("event publish complete....");
    }
}
