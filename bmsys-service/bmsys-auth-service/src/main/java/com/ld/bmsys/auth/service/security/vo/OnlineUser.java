package com.ld.bmsys.auth.service.security.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author ld
 * @Date 2020/4/18 16:26
 */
@Data
@Accessors(chain = true)
public class OnlineUser implements Serializable {

    private Integer userId;

    private String username;

    private String ip;

    private LocalDateTime loginTime;

    public OnlineUser() {
    }

    public OnlineUser(Integer userId, String username, String ip, LocalDateTime loginTime) {
        this.userId = userId;
        this.username = username;
        this.ip = ip;
        this.loginTime = loginTime;
    }
}
