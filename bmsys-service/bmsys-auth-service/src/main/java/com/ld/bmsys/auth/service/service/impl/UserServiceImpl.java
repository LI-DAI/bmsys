package com.ld.bmsys.auth.service.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.api.entity.UserRole;
import com.ld.bmsys.auth.api.vo.SearchConditionVO;
import com.ld.bmsys.auth.service.dao.UserMapper;
import com.ld.bmsys.auth.service.dao.UserRoleMapper;
import com.ld.bmsys.auth.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/5 16:58
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleMapper userRoleMapper;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.save(user);
    }

    @Override
    public int insertUserRole(List<UserRole> userRoles) {
        return userRoleMapper.batchInsert(userRoles);
    }

    @Override
    public int deleteUserRoleByUserId(List<Integer> userIds) {
        return userRoleMapper.deleteUserRoleByUserId(userIds);
    }

    @Override
    public int deleteUserRoleByRoleId(List<Integer> roleIds) {
        return userRoleMapper.deleteUserRoleByRoleId(roleIds);
    }

    @Override
    public Page<User> getUserList(SearchConditionVO search) {
        Page<User> page = new Page<>(search.getPageNum(), search.getPageSize());
        LambdaQueryWrapper<User> query = Wrappers.<User>query().lambda();
        if(StrUtil.isNotBlank(search.getUsername())){
            query.like(User::getUsername, search.getUsername());
        }
        if(StrUtil.isNotBlank(search.getPhone())){
            query.eq(User::getPhone, search.getPhone());
        }
        return userMapper.selectPage(page, query);
    }

}
