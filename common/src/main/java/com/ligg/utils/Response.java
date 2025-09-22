package com.ligg.utils;

import com.ligg.statuEnum.BusinessStates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author Ligg
 * @Time 2025/9/22
 * <p>
 * 统一响应结果类
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Response<T> success(BusinessStates status, T data) {
        return new Response<>(status.getCode(), status.getMessage(), data);
    }

    public static <T> Response<T> success(BusinessStates status) {
        return new Response<>(status.getCode(), status.getMessage(), null);
    }

    public static <T> Response<T> error(BusinessStates status) {
        return new Response<>(status.getCode(), status.getMessage(), null);
    }

    public static <T> Response<T> error(BusinessStates status, String message) {
        return new Response<>(status.getCode(), message, null);
    }
}
