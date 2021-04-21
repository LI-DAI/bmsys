package com.ld.bmsys.auth.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.api.vo.SearchConditionVO;

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
     * @param user 用户信息
     * @return
     */
    boolean register(User user);

    /**
     * 修改用户信息
     *
     * @param user 信息
     */
    void updateUser(User user);

    /**
     * 获取用户列表
     *
     * @param searchConditionVO 搜索条件
     * @return
     */
    Page<User> getUserList(SearchConditionVO searchConditionVO);
}
