package com.ld.bmsys.auth.service.handler;

import com.ld.bmsys.common.entity.Result;
import com.ld.bmsys.common.enums.ResultCode;
import com.ld.bmsys.common.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author LD
 * @Date 2021/4/18 10:43
 * <p>
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 未知异常处理
     */
    @ExceptionHandler(value = Throwable.class)
    public Result<String> runtimeException(Throwable e) {
        log.error(getStackTrace(e));
        return Result.fail(e.getMessage());
    }

    /**
     * 无权限异常处理
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result<String> accessDeniedException(AccessDeniedException e) {
        log.error(getStackTrace(e));
        return Result.fail(ResultCode.UN_AUTHORIZATION);
    }

    /**
     * 密码错误异常处理
     */
    @ExceptionHandler(value = BadCredentialsException.class)
    public Result<String> badCredentialsException(BadCredentialsException e) {
        log.error(getStackTrace(e));
        return Result.fail(ResultCode.BAD_CREDENTIALS);
    }

    /**
     * 参数校验异常处理
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(getStackTrace(e));
        /*Map<String, String> errorMap = e.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(error -> ((FieldError) error).getField(), ObjectError::getDefaultMessage));
        List<String> errorList = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).collect(Collectors.toList());*/
        return Result.fail(ResultCode.FAILURE, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(value = BadRequestException.class)
    public Result<String> badRequestException(BadRequestException e) {
        log.error(getStackTrace(e));
        return Result.fail(ResultCode.FAILURE, e.getMessage());
    }

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
