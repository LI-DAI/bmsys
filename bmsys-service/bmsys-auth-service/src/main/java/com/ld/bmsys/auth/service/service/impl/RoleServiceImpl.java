package com.ld.bmsys.auth.service.service.impl;

import com.ld.bmsys.auth.api.entity.Role;
import com.ld.bmsys.auth.api.entity.RoleMenu;
import com.ld.bmsys.auth.service.dao.RoleMapper;
import com.ld.bmsys.auth.service.dao.RoleMenuMapper;
import com.ld.bmsys.auth.service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/26 15:06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleMenuMapper roleMenuMapper;

    public RoleServiceImpl(RoleMapper roleMapper, RoleMenuMapper roleMenuMapper) {
        this.roleMapper = roleMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    public int insertRole(Role role) {
//        role.setRoleKey("ROLE_" + role.getRoleKey());
        return roleMapper.insertSelective(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteRole(List<String> ids) {
        return 0;
    }

    @Override
    public int insertRoleMenu(List<RoleMenu> roleMenus) {
        return roleMenuMapper.batchInsert(roleMenus);
    }

    @Override
    public int deleteByRoleIds(List<Integer> roleIds) {
        return 0;
    }
}
