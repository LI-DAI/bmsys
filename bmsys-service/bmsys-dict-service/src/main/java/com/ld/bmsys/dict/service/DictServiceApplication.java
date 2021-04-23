package com.ld.bmsys.dict.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author ld
 * @Date 2021/4/16 9:23
 * <p>
 * 添加扫描feign的api,否则无法注入，另外还需要扫描自身bean
 * 另外，不知道为什么EnableFeignClients注解扫描的包就没生效
 */
@ComponentScan(basePackages = {
        "com.ld.bmsys"
//        "com.ld.bmsys.auth.api.feign",
//        "com.ld.bmsys.dict.service"
})
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"com.ld.bmsys.auth.api.feign"})
public class DictServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictServiceApplication.class, args);
    }

}
