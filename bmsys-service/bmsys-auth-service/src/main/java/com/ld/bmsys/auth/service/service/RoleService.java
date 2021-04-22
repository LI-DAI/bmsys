package com.ld.bmsys.auth.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ld.bmsys.auth.api.entity.Role;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/26 15:06
 */
public interface RoleService extends IService<Role> {

    /**
     * 删除角色
     *
     * @param roleIds 角色ID集合
     * @return
     */
    void deleteRoles(List<Integer> roleIds);

    /**
     * 添加角色
     *
     * @param role 角色信息
     * @return
     */
    boolean addRole(Role role);

    /**
     * 清空缓存
     *
     * @return
     */
    boolean clearCache();

}
