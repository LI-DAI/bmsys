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
     * @param roleIds
     * @return
     */
    void deleteRoles(List<Integer> roleIds);

}
