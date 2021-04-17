package com.ld.bmsys.auth.service.service;

import com.ld.bmsys.auth.api.entity.Menu;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/26 15:12
 */
public interface MenuService {

    /**
     * 数据库加载所有权限
     *
     * @return
     */
    List<Menu> loadAllMenus();
}
