package com.ld.bmsys.auth.service.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ld.bmsys.auth.api.entity.Menu;
import com.ld.bmsys.auth.service.dao.MenuMapper;
import com.ld.bmsys.auth.service.service.MenuService;
import com.ld.bmsys.auth.service.utils.RedisUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LD
 * @date 2020/3/26 15:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final MenuMapper menuMapper;
    private final RedisUtil redisUtil;

    public MenuServiceImpl(MenuMapper menuMapper,RedisUtil redisUtil) {
        this.menuMapper = menuMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    @Cacheable(cacheNames = "menu", key = "'all'")
    public List<Menu> loadAllMenus() {
        List<Menu> menus = menuMapper.getAllMenus();
        return treeMenu(menus, 0);
    }

    @Override
    @Cacheable(cacheNames = "menu", key = "#p0")
    public List<Menu> getMenusByUserId(Integer userId) {
        return menuMapper.getMenuByUserId(userId);
    }

    private List<Menu> treeMenu(List<Menu> menus, Integer parentId) {
        List<Menu> result = new ArrayList<>();
        for (Menu menu : menus) {
            if (parentId.equals(menu.getParentId())) {
                menu.setChildren(treeMenu(menus, menu.getMenuId()));
                result.add(menu);
            }
        }
        return result;
    }


    public void clearMenuCache(List<Integer> userIds){
        if(CollectionUtil.isEmpty(userIds)){
        }
    }
}
