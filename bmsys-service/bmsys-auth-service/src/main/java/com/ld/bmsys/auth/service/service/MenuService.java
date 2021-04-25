package com.ld.bmsys.auth.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ld.bmsys.auth.api.entity.Menu;

import java.util.List;

/**
 * @author LD
 * @date 2020/3/26 15:12
 */
public interface MenuService extends IService<Menu> {

    /**
     * 数据库加载所有权限
     *
     * @return
     */
    List<Menu> loadAllMenus();

    /**
     * 获取用户权限
     *
     * @param userId 用户唯一ID
     * @return
     */
    List<Menu> getMenusByUserId(Integer userId);
}
