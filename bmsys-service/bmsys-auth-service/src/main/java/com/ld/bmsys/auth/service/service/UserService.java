package com.ld.bmsys.auth.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.api.entity.UserRole;
import com.ld.bmsys.auth.api.vo.SearchConditionVO;
import com.ld.bmsys.common.entity.PageData;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/5 16:57
 */
public interface UserService extends IService<User> {

    /**
     * 获取User
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 注册
     *
     * @param user
     * @return
     */
    boolean register(User user);

    int insertUserRole(List<UserRole> userRoles);

    int deleteUserRoleByUserId(List<Integer> userIds);

    int deleteUserRoleByRoleId(List<Integer> roleIds);

    Page<User> getUserList(SearchConditionVO searchConditionVO);
}
