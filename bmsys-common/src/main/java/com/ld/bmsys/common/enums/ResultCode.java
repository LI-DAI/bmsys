package com.ld.bmsys.common.enums;

import com.ld.bmsys.common.entity.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ld
 * @Date 2020/3/5 10:57
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

    SUCCESS(200, "操作成功"),
    FAILURE(500, "操作失败"),

    UN_AUTHENTICATION(-100, "未认证"),
    UN_AUTHORIZATION(-101, "未授权"),
    BAD_CREDENTIALS(-102, "用户名或密码错误"),
    LOCKED(-103, "账户已锁定"),
    EXPIRE_TOKEN(-200, "无效的Token");

    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String message;

}
