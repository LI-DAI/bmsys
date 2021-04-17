package com.ld.bmsys.auth.service.dao;

import com.ld.bmsys.auth.api.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/5 21:39
 */
public interface MenuMapper {

    /**
     * 获取权限菜单
     *
     * @param userId
     * @return
     */
    List<Menu> getMenuByUserId(@Param("userId") Integer userId);

    /**
     * 获取所有菜单
     *
     * @return
     */
    @Select(value = "select * from bmsys_menu")
    List<Menu> getAllMenus();
}