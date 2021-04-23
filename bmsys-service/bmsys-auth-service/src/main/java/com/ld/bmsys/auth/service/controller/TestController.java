package com.ld.bmsys.auth.service.controller;

import com.ld.bmsys.auth.service.security.anon.AnonymousAccess;
import com.ld.bmsys.auth.service.utils.IpUtil;
import com.ld.bmsys.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ld
 * @Date 2021/4/15 16:54
 */
@RestController
@RequestMapping("/auth/test")
@Api(tags = "测试")
public class TestController {

    @GetMapping("/noCheck")
    public Result<String> noCheck() {
        return Result.data("no check");
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public Result<String> testAuthority() {
        return Result.data("this is test");
    }


    @GetMapping("/role")
    @Secured(value = "ROLE_COMMON")
    @AnonymousAccess
    public Result<String> testRole() {
        return Result.data("this is test");
    }

    @GetMapping("/ip")
    @AnonymousAccess
    public Result<String> testIp(HttpServletRequest request) {
        String ip = IpUtil.getIp(request);

//        ip = "112.86.218.124";

        String cityInfo = IpUtil.getCityInfo(ip, true);

        String httpCityInfo = IpUtil.getCityInfo(ip, false);

        return Result.data("this is test");
    }


    @PostMapping("/test1")
    @ApiOperation(value = "测试2")
    @Cacheable(cacheNames = "userManage")
    public Result<String> test(String name, Integer age) {
        return Result.data("hello user" + name + age);
    }

    @PostMapping("/test2")
    @ApiOperation(value = "测试1")
    @Cacheable(cacheNames = "Test")
    public Result<String> test(String test) {
        return Result.data("hello user" + test);
    }

}
