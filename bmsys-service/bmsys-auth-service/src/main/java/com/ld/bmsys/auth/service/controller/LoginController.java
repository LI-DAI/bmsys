package com.ld.bmsys.auth.service.controller;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.ImmutableMap;
import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.service.security.SecurityProperties;
import com.ld.bmsys.auth.service.security.vo.AuthUser;
import com.ld.bmsys.auth.service.service.OnlineUserService;
import com.ld.bmsys.common.entity.Result;
import com.ld.bmsys.common.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.ld.bmsys.common.constant.CommonConstant.AUTHENTICATION;

/**
 * @Author ld
 * @Date 2020/3/22 20:31
 */
@RestController
@RequestMapping("")
@Api(tags = "登陆注销")
public class LoginController {

    public final AuthenticationManager authenticationManager;
    private final StringRedisTemplate redisTemplate;
    private final SecurityProperties securityProperties;
    private final OnlineUserService onlineUserService;

    public LoginController(AuthenticationManager authenticationManager, StringRedisTemplate redisTemplate, SecurityProperties securityProperties, OnlineUserService onlineUserService) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
        this.securityProperties = securityProperties;
        this.onlineUserService = onlineUserService;
    }

    @PostMapping("/login")
    @ApiOperation("登陆")
    public Result<Object> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(upToken);
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String token = securityProperties.getOnlineKey() + JwtTokenUtil.createToken(username);
        Map<String, Object> authInfo = ImmutableMap.of("token", token, "user", authUser);
        return Result.data(authInfo, "登陆成功");
    }

    @GetMapping("/logout")
    @ApiOperation("注销")
    public Result<Object> logout(HttpServletRequest request) {
        String token = request.getHeader(AUTHENTICATION);
        Map<String, Object> objectMap = JwtTokenUtil.parseJwtToken(token);
        redisTemplate.delete(token);
        redisTemplate.delete(MapUtil.getStr(objectMap, "username"));
        return Result.success("注销成功");
    }
}
