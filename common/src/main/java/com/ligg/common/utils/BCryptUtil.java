package com.ligg.common.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
public final class BCryptUtil {

    // 默认计算强度 (work factor)，范围 4~31，值越大越安全但越慢
    private static final int DEFAULT_LOG_ROUNDS = 12;

    private BCryptUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 密码加密
     * @param plainPassword 明文密码
     * @return 加密后的哈希密码
     */
    public static String encrypt(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(DEFAULT_LOG_ROUNDS));
    }

    /**
     * 验证密码是否正确
     * @param plainPassword 待验证的明文密码
     * @param hashedPassword 已加密的哈希密码
     * @return true 验证成功，false 验证失败
     */
    public static boolean verify(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
