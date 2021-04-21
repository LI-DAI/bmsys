package com.ld.bmsys.auth.service.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.api.vo.SearchConditionVO;
import com.ld.bmsys.auth.service.dao.UserMapper;
import com.ld.bmsys.auth.service.service.UserService;
import com.ld.bmsys.common.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author ld
 * @Date 2020/3/5 16:58
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "userInfo")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Cacheable(key = "#username")
    public User findUserByUsername(String username) {
        Wrapper<User> wrapper = Wrappers.<User>query().lambda().eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public boolean register(User user) {
        Integer count = userMapper.selectCount(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (count > 0) {
            throw new BadRequestException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.save(user);
    }

    @CacheEvict(key = "#p0.username")
    @Override
    public User updateUser(User user) {
        userMapper.updateById(user);
        return user;
    }


    @Override
    public Page<User> getUserList(SearchConditionVO search, Page page) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery()
                .and(StrUtil.isNotBlank(search.getUsername()), query -> query.like(User::getUsername, search.getUsername()))
                .and(StrUtil.isNotBlank(search.getPhone()), query -> query.eq(User::getPhone, search.getPhone()));
        return userMapper.selectPage(page, lambdaQueryWrapper);
    }

}
