package com.ld.bmsys.auth.service.controller;

import com.ld.bmsys.common.entity.Result;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ld
 * @Date 2021/4/15 16:54
 */
@RestController
@RequestMapping("/auth/test")
public class TestController {

    @GetMapping("/noCheck")
    public Result<String> noCheck() {
        return Result.data("no check");
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('user::add')")
    public Result<String> testAuthority() {
        return Result.data("this is test");
    }


    @GetMapping("/role")
    @Secured(value = "ROLE_COMMON")
    public Result<String> testRole() {
        return Result.data("this is test");
    }
}
