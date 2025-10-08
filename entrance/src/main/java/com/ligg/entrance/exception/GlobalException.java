/**
 * @Author Ligg
 * @Time 2025/9/25
 **/
package com.ligg.entrance.exception;

import com.ligg.common.enums.BusinessStates;
import com.ligg.common.utils.Response;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Response<String> handleMaxSizeException(MaxUploadSizeExceededException e) {
        log.error("上传文件大小超出限制", e);
        return Response.error(BusinessStates.FILE_UPLOAD_FAILED, "上传文件大小超出限制");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("参数验证失败:{}", e.getMessage());
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return Response.error(BusinessStates.VALIDATION_FAILED, message);
    }
}
