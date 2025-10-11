/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
package com.ligg.common.service;


import com.ligg.common.module.dto.CaptchaDto;

/**
 * 验证码
 */
public interface CaptchaService {

    /**
     * 验证码校验
     */
    boolean verifyCaptcha(String code,String uuid);

    /**
     * 创建验证码
     */
    CaptchaDto createCaptcha(String lastTimeUid);
}
