package com.ld.bmsys.auth.service.security.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author ld
 * @Date 2020/4/18 16:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlineUser {

    private Integer userId;

    private String username;

    private String ip;

    private LocalDateTime loginTime;
}
