/**
 * @Author Ligg
 * @Time 2025/10/6
 **/
package com.ligg.common.constants;

public class Constant {

    /**
     * 授权标头
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 账号
     */
    public static final String ACCOUNT = "account";

    /**
     * Token
     */
    public static final String TOKEN = "Token:";

    /**
     * 过期时间6小时
     */
    public static final long EXPIRE = 6 * 60 * 60;

    /**
     * 三分钟
     */
    public static final long THREE_MINUTES = 3 * 60 * 1000;

    /**
     * Token Key
     */
    public static final String TOKEN_KEY = "Ligg"; //临时秘钥

    /**
     * 文件大小
     */
    public static final long FILE_SIZE = 1024 * 1024 * 2;

    /**
     * 头像文件保持路径
     */
    public static final String AVATAR_FILE_PATH = "/Avatar";

    /**
     * 精选商品文件保持路径
     */
    public static final String FEATURED_FILE_PATH = "/Featured";

    /**
     * 轮播图保持路径
     */
    public static final String CAROUSEL_FILE_PATH = "/Carousel";

    /**
     * 图片前缀
     */
    public static final String IMAGE_RELATIVE_PATH = "/api/image";

    /**
     * 验证码前缀
     */
    public static final String CAPTCHA_REDIS_KEY = "captcha:";
}
