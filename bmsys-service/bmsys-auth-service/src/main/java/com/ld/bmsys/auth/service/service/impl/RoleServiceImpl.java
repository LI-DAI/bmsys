package com.ld.bmsys.auth.service.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ld.bmsys.auth.api.entity.Role;
import com.ld.bmsys.auth.api.entity.RoleMenu;
import com.ld.bmsys.auth.api.entity.UserRole;
import com.ld.bmsys.auth.service.dao.RoleMapper;
import com.ld.bmsys.auth.service.dao.RoleMenuMapper;
import com.ld.bmsys.auth.service.dao.UserRoleMapper;
import com.ld.bmsys.auth.service.service.RoleService;
import com.ld.bmsys.common.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/26 15:06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleMenuMapper roleMenuMapper;

    private final UserRoleMapper userRoleMapper;

    public RoleServiceImpl(RoleMenuMapper roleMenuMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper) {
        this.roleMenuMapper = roleMenuMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public void deleteRoles(List<Integer> roleIds) {
        //判断角色是否被占用
        Integer count = userRoleMapper.selectCount(Wrappers.<UserRole>lambdaQuery().in(UserRole::getRoleId, roleIds));
        if (count > 0) {
            throw new RuntimeException("角色正在被占用，无法删除");
        }
        this.removeByIds(roleIds);
        roleMenuMapper.delete(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, roleIds));
    }

    @Override
    public boolean addRole(Role role) {
        Integer count = roleMapper.selectCount(Wrappers.<Role>lambdaQuery().eq(Role::getRoleKey, role.getRoleKey()));
        if (count > 0) {
            throw new BadRequestException("role key 已存在");
        }
        this.save(role);
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }
}
