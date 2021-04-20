package com.ld.bmsys.auth.service.service;

import com.ld.bmsys.auth.service.security.vo.AuthUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ld
 * @Date 2020/4/18 16:29
 */
public interface OnlineUserService {

    /**
     * 判断是否已存在登陆用户
     *
     * @param username
     * @param token
     */
    void checkOnlineUser(String username, String token);

    /**
     * 保存登陆用户
     *
     * @param user /
     * @param token /
     */
    void saveOnlineUser(AuthUser user, String token, HttpServletRequest request);
}
