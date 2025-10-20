/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.module.dto.CaptchaDto;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.CaptchaService;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

/**
 * 验证码
 */
@Tag(name = "验证码接口")
@RestController
@RequestMapping("/api/client/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    /**
     * 获取验证码
     * @param lastTimeUid 上一次请求的uuid,
     *                    如果调用方传递会先删除旧验证码在生成.
     */
    @SneakyThrows
    @GetMapping
    @Operation(summary = "获取验证码")
    public Response<CaptchaDto> getCaptcha(@RequestParam(required = false) String lastTimeUid) {
        Thread.sleep(1000);
        return Response.success(BusinessStates.SUCCESS,
                captchaService.createCaptcha(lastTimeUid));
    }
}
