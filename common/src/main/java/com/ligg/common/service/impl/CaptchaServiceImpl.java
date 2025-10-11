package com.ligg.common.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.ligg.common.constants.Constant;
import com.ligg.common.module.dto.CaptchaDto;
import com.ligg.common.service.CaptchaService;
import com.ligg.common.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    final RedisUtil redisUtil;

    /**
     * 验证码校验
     *
     * @param code 验证码
     * @param uuid uuid
     * @return true/false
     */
    @Override
    public boolean verifyCaptcha(String code, String uuid) {
        String redisCode = (String) redisUtil.get(Constant.CAPTCHA_REDIS_KEY + uuid);
        if (!StringUtils.hasText(redisCode)) {
            return false;
        }
        boolean equals = redisCode.equalsIgnoreCase(code);
        if (!equals) {
            deleteCaptcha(uuid);
            return false;
        }
        deleteCaptcha(uuid);
        return true;
    }

    /**
     * 生成验证码,并保存到redis
     */
    @Override
    public CaptchaDto createCaptcha(String lastTimeUid) {
        if (StringUtils.hasText(lastTimeUid)) {
            deleteCaptcha(lastTimeUid);
        }
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(150, 48, 6, 20);
        String uuid = UUID.randomUUID().toString();

        redisUtil.set(Constant.CAPTCHA_REDIS_KEY + uuid, captcha.getCode(), 3, TimeUnit.MINUTES);
        CaptchaDto captchaDto = new CaptchaDto();
        captchaDto.setUuid(uuid);
        captchaDto.setCaptcha(captcha.getImageBase64());
        return captchaDto;
    }

    /**
     * 删除redis中的验证码
     */
    private void deleteCaptcha(String uuid) {
        redisUtil.del(Constant.CAPTCHA_REDIS_KEY + uuid);
    }
}
