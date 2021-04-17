package com.ld.bmsys.auth.service.service;

import com.ld.bmsys.auth.api.entity.Role;
import com.ld.bmsys.auth.api.entity.RoleMenu;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/26 15:06
 */
public interface RoleService {

    int insertRole(Role role);

    int updateRole(Role role);

    int deleteRole(List<String> ids);

    int insertRoleMenu(List<RoleMenu> roleMenus);

    /**
     * 删除
     * @param roleIds 角色ID
     * @return
     */
    int deleteByRoleIds(List<Integer> roleIds);
}
