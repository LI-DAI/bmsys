package com.ld.bmsys.auth.service.security;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ld.bmsys.auth.api.entity.Menu;
import com.ld.bmsys.auth.api.entity.Role;
import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.service.dao.MenuMapper;
import com.ld.bmsys.auth.service.dao.RoleMapper;
import com.ld.bmsys.auth.service.dao.UserMapper;
import com.ld.bmsys.auth.service.security.vo.AuthUser;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ld.bmsys.common.constant.CommonConstant.getAuthoritiesCacheName;
import static java.util.stream.Collectors.toList;

/**
 * @Author ld
 * @Date 2021/4/16 9:23
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final MenuMapper menuMapper;

    private final StringRedisTemplate redisTemplate;

    private final SecurityProperties properties;

    public UserDetailsServiceImpl(UserMapper userMapper, RoleMapper roleMapper, MenuMapper menuMapper, StringRedisTemplate redisTemplate, SecurityProperties properties) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(Wrappers.<User>query().lambda().eq(User::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>(10);
        String authoritiesCacheName = getAuthoritiesCacheName(username);
        if (redisTemplate.hasKey(authoritiesCacheName)) {
            //直接从缓存中取
            String authoritiesStr = redisTemplate.opsForValue().get(username);
            if (StrUtil.isNotBlank(authoritiesStr)) {
                List<SimpleGrantedAuthority> grantedAuthorities = JSON.parseArray(authoritiesStr, SimpleGrantedAuthority.class);
                if (grantedAuthorities != null && !grantedAuthorities.isEmpty()) {
                    authorities.addAll(grantedAuthorities);
                }
            }
        } else {
            List<Role> roles = roleMapper.getRolesByUserId(user.getUserId());
            roles.stream().map(role -> "ROLE_" + role.getRoleKey())
                    .forEach(key -> authorities.addAll(AuthorityUtils.createAuthorityList(key)));

            List<Menu> menus = menuMapper.getMenuByUserId(user.getUserId());
            List<GrantedAuthority> perms = menus.stream().filter(Objects::nonNull).map(Menu::getPerms)
                    .distinct().map(SimpleGrantedAuthority::new).collect(toList());

            authorities.addAll(perms);

            redisTemplate.opsForValue().set(authoritiesCacheName, JSON.toJSONString(authorities), properties.getTokenValidityInHours(), TimeUnit.HOURS);

        }
        return new AuthUser(user.getUserId(), username, user.getEmail(), user.getPassword(), authorities);

    }

}
