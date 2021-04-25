package com.ld.bmsys.auth.service.controller;

import com.ld.bmsys.auth.api.entity.Menu;
import com.ld.bmsys.auth.service.service.MenuService;
import com.ld.bmsys.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LD
 * @date 2021/4/22 10:39
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单管理")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/find")
    @ApiOperation(value = "获取所有权限")
    public Result<List<Menu>> findUserByUsername() {
        return Result.data(menuService.loadAllMenus());
    }
}
