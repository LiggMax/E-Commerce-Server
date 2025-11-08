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
     * 邮箱
     */
    public static final String EMAIL = "email";

    /**
     * Token
     */
    public static final String TOKEN = "Token:";

    /**
     * 过期时间6小时
     */
    public static final long EXPIRE = 6 * 60 * 60;

    /**
     * 邮件过期时间
     */
    public static final long EMAIL_EXPIRE = 15;

    /**
     * 注册前缀
     */
    public static final String REGISTER = "register:";

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
     * 评论
     */
    public static final String COMMENT_FILE_PATH = "/Comment";

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

    /**
     * 邮件验证码前缀
     */
    public static final String EMAIL_CAPTCHA_REDIS_KEY = "email:captcha:";
}
