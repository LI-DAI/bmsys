package com.ld.bmsys.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LD
 * @date 2020/7/20 14:05
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/1")
    public String test() {
        return "HELLO";
    }
}
