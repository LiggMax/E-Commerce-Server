/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
package com.ligg.common.dto;

import lombok.Data;

/**
 * 验证码
 */
@Data
public class CaptchaDto {
    private String uuid;
    private String captcha;
}
