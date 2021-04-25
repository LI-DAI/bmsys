package com.ld.bmsys.auth.service.controller;

import com.ld.bmsys.auth.api.entity.Role;
import com.ld.bmsys.auth.api.entity.RoleMenu;
import com.ld.bmsys.auth.service.service.RoleMenuService;
import com.ld.bmsys.auth.service.service.RoleService;
import com.ld.bmsys.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author LD
 * @date 2020/3/26 14:35
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {

    private final RoleService roleService;

    private final RoleMenuService roleMenuService;

    public RoleController(RoleService roleService, RoleMenuService roleMenuService) {
        this.roleService = roleService;
        this.roleMenuService = roleMenuService;
    }

    @PostMapping("/insert")
    @ApiOperation("新增角色")
    public Result<Boolean> addRole(@RequestBody @Valid Role role) {
        return Result.data(roleService.addRole(role));
    }

    @PostMapping("/add/menus")
    @ApiOperation("为角色添加权限菜单")
    public Result<Boolean> addRoleMenu(List<RoleMenu> roleMenus) {
        return Result.data(roleMenuService.saveBatch(roleMenus));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除角色")
    public Result<Boolean> deleteRole(List<Integer> roleIds) {
        roleService.deleteRoles(roleIds);
        return Result.success();
    }
}
