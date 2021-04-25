package com.ld.bmsys.dict.service.demo.event;

import com.ld.bmsys.dict.service.demo.entity.Person;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author LD
 * @date 2021/4/23 14:37
 */
public class PersonRegisterEvent extends ApplicationEvent {

    @Getter
    private Person person;

    public PersonRegisterEvent(Object source, Person person) {
        super(source);
        this.person = person;
    }
}
