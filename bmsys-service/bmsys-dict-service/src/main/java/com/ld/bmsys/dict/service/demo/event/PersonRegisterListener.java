package com.ld.bmsys.dict.service.demo.event;

import com.ld.bmsys.dict.service.demo.entity.Person;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author LD
 * @date 2021/4/23 14:46
 * <p>
 * 监听用户注册事件,两种写法,另一种是使用注解@EventListener实现
 * 用户注册事件发布时,两个监听器都会出发,
 * 但是执行顺序是无序的,若要实现有序,需要实现{@link org.springframework.context.event.SmartApplicationListener}
 */
@Component
public class PersonRegisterListener implements ApplicationListener<PersonRegisterEvent> {


    @Async
    @Override
    public void onApplicationEvent(PersonRegisterEvent event) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person = event.getPerson();

        System.out.println(String.format("implements ApplicationListener注册信息，用户名：%s , 年龄：%d ", person.getName(), person.getAge()));

    }
}
