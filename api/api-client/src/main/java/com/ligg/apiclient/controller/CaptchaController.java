/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.dto.CaptchaDto;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.CaptchaService;
import com.ligg.common.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 验证码
 */
@RestController
@RequestMapping("/api/client/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    final CaptchaService captchaService;

    /**
     * 获取验证码
     */
    @GetMapping
    public Response<CaptchaDto> getCaptcha() {
        //生成验证码对象
        return Response.success(BusinessStates.SUCCESS,
                captchaService.createCaptcha());
    }
}
