package com.ld.bmsys.auth.service.handler;

import com.ld.bmsys.common.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author ld
 * @Date 2020/3/11 10:27
 * <p>
 * 全局异常处理
 */
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public Result<String> runtimeException(Exception e) {
        return Result.fail(e.getMessage());
    }
}
