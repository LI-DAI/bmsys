package com.ld.bmsys.auth.service.controller;

import com.ld.bmsys.auth.api.entity.Role;
import com.ld.bmsys.auth.service.service.RoleService;
import com.ld.bmsys.common.entity.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ld
 * @Date 2020/3/26 14:35
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/insert")
    public Result<Integer> insertRole(Role role) {
        return Result.data(roleService.insertRole(role));
    }


}
