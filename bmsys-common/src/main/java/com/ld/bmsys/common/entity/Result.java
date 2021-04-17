package com.ld.bmsys.common.entity;

import com.ld.bmsys.common.constant.CommonConstant;
import com.ld.bmsys.common.enums.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author ld
 * @Date 2020/3/5 10:56
 */
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    private Result(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private Result(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Result<T> success() {
        return success(ResultCode.SUCCESS.getMessage());
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(ResultCode.SUCCESS, msg);
    }

    public static <T> Result<T> data(T data) {
        return data(data, ResultCode.SUCCESS.getMessage());
    }

    public static <T> Result<T> data(T data, String msg) {
        return data(ResultCode.SUCCESS.getCode(), data, msg);
    }

    public static <T> Result<T> data(int code, T data, String msg) {
        return new Result<>(code, data, data == null ? CommonConstant.DEFAULT_NULL_MESSAGE : msg);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCode.FAILURE, msg);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, null, msg);
    }

    public static <T> Result<T> fail(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    public static <T> Result<T> fail(IResultCode resultCode, String msg) {
        return new Result<>(resultCode, msg);
    }


}
