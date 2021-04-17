package com.ld.bmsys.auth.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ld.bmsys.auth.api.entity.RoleMenu;
import com.ld.bmsys.auth.service.dao.RoleMenuMapper;
import com.ld.bmsys.auth.service.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author LD
 * @Date 2021/4/17 20:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
