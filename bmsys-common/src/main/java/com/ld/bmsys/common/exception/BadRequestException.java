package com.ld.bmsys.common.exception;

/**
 * @Author LD
 * @Date 2021/4/18 11:09
 */
public class BadRequestException extends RuntimeException{

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
