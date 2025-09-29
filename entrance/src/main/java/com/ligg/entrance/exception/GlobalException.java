package com.ligg.entrance.exception;

/**
 * @Author Ligg
 * @Time 2025/9/25
 **/

import com.ligg.common.enums.BusinessStates;
import com.ligg.common.utils.Response;
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
    public Response<String> handleMaxSizeException(MaxUploadSizeExceededException e){
        log.error("上传文件大小超出限制",e);
        return Response.error(BusinessStates.FILE_UPLOAD_FAILED,"上传文件大小超出限制");
    }
}
