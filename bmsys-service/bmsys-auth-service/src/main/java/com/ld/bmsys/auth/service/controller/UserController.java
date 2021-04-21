package com.ld.bmsys.auth.service.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.api.entity.UserRole;
import com.ld.bmsys.auth.api.vo.SearchConditionVO;
import com.ld.bmsys.auth.service.service.UserRoleService;
import com.ld.bmsys.auth.service.service.UserService;
import com.ld.bmsys.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/5 13:56
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
@CacheConfig(cacheNames = "user")
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;

    public UserController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/find")
    @ApiOperation(value = "获取user")
    public Result<User> findUserByUsername(@RequestParam("username") String username) {
        return Result.data(userService.findUserByUsername(username));
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册")
    @CacheEvict(cacheNames = "user", key = "'userList'", allEntries = true)
    public Result<Boolean> register(@RequestBody @Valid User user) {
        return Result.data(userService.register(user));
    }

    @PostMapping("/add/user_role")
    @ApiOperation("新增UserRole")
    public Result<Boolean> insertUserRole(@RequestBody List<UserRole> userRoles) {
        return Result.data(userRoleService.saveBatch(userRoles));
    }

    @PostMapping("/list")
    @ApiOperation("获取用户列表")
    @Cacheable(cacheNames = "user", key = "'userList' + #searchConditionVO")
    public Result<Page<User>> getUserList(@RequestBody SearchConditionVO searchConditionVO) {
        return Result.data(userService.getUserList(searchConditionVO));
    }

}
