package com.ld.bmsys.auth.service.service.impl;

import com.ld.bmsys.auth.api.entity.Menu;
import com.ld.bmsys.auth.service.dao.MenuMapper;
import com.ld.bmsys.auth.service.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/26 15:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> loadAllMenus() {
        return menuMapper.getAllMenus();
    }
}
