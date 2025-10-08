/**
 * @Author Ligg
 * @Time 2025/9/22
 * <p>
 * 统一响应结果类
 **/
package com.ligg.common.utils;

import com.ligg.common.enums.BusinessStates;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    /**
     * 状态码
     */
    @Schema(description = "状态码")
    private Integer code;

    /**
     * 状态信息
     */
    @Schema(description = "状态信息")
    private String message;

    /**
     * 数据
     */
    @Schema(description = "数据")
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
