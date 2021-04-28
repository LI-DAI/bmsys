package com.ld.bmsys.dict.service.controller;

import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.api.feign.UserApi;
import com.ld.bmsys.common.entity.Result;
import com.ld.bmsys.dict.service.service.CodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LD
 * @date 2020/3/5 20:14
 */
@Slf4j
@RestController
@RequestMapping("/code")
@Api(value = "字典操作")
public class CodeController {

    private final UserApi userApi;

    private final CodeService codeService;

    public CodeController(UserApi userApi, CodeService codeService) {
        this.userApi = userApi;
        this.codeService = codeService;
    }

    @GetMapping("/get")
    @ApiOperation("获取用户")
    public Result<User> findUser(@RequestParam("username") String username) {
        return userApi.findUserByUsername(username);
    }

    public void test() {
        codeService.test();
    }

}
