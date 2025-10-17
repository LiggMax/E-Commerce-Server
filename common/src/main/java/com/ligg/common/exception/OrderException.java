package com.ligg.common.exception;

import lombok.Getter;

/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
public class OrderException extends RuntimeException {
    @Getter
    private int code;
    private final String message;

    public OrderException(String message) {
        super(message);
        this.message = message;
    }

    public OrderException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public OrderException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
