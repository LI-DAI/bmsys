package com.ld.bmsys.dict.service.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author LD
 * @date 2021/4/28 10:55
 */
@Component
public class SpringContentUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContentUtil.applicationContext = applicationContext;
    }


    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }
}
