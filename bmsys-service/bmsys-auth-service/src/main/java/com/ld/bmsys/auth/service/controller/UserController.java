package com.ld.bmsys.auth.service.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.api.entity.UserRole;
import com.ld.bmsys.auth.api.vo.SearchConditionVO;
import com.ld.bmsys.auth.service.service.UserService;
import com.ld.bmsys.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/5 13:56
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/hello")
    @ApiOperation(value = "测试")
    public Result<String> test() {
        return Result.data("hello feign");
    }

    @GetMapping("/find")
    @ApiOperation(value = "获取user")
    public Result<User> findUserByUsername(@RequestParam("username") String username) {
        return Result.data(userService.findUserByUsername(username));
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public Result<Boolean> register(@RequestBody User user) {
        return Result.data(userService.register(user));
    }

    @PostMapping("/add/user_role")
    @ApiOperation("新增UserRole")
    public Result<Integer> insertUserRole(@RequestBody List<UserRole> userRoles) {
        return Result.data(userService.insertUserRole(userRoles));
    }

    @PostMapping("/list")
    @ApiOperation("获取用户列表")
    public Result<Page<User>> getUserList(@RequestBody SearchConditionVO searchConditionVO) {
        return Result.data(userService.getUserList(searchConditionVO));
    }

}
