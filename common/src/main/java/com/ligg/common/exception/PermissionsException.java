package com.ligg.common.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

/**
 * @author Ligg
 * @Time 2025/10/22
 **/
public class PermissionsException extends RuntimeException {
    @Getter
    private int code;
    private final String message;

    public PermissionsException(String message) {
        super(message);
        this.message = message;
    }

    public PermissionsException(String message,int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public PermissionsException(String message, HttpServletResponse response) {
        super(message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
