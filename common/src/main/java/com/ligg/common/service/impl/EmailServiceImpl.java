package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.constants.Constant;
import com.ligg.common.module.entity.EmailEntity;
import com.ligg.common.mapper.EmailMapper;
import com.ligg.common.service.EmailService;
import com.ligg.common.utils.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author LiGG
 * @Time 2025/10/8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl extends ServiceImpl<EmailMapper, EmailEntity> implements EmailService {

    @Value("${spring.mail.username}")
    private String FROM;

    @Resource
    private JavaMailSender mailSender;

    private final RedisUtil redisUtil;

    private final EmailMapper emailMapper;

    private final SpringTemplateEngine templateEngine;

    @Override
    @Async("emailTaskExecutor")
    public void sendVerificationCode(String toEmail) {
        if (redisUtil.hasKey(Constant.EMAIL_CAPTCHA_REDIS_KEY + toEmail)) {
            log.warn("邮箱验证码已发送,请勿重复发送");
            return;
        }

        //构建邮件内容
        int code = ThreadLocalRandom.current().nextInt(100000, 999999);
        long expire = Constant.EMAIL_EXPIRE;
        String verifyUrl = "logg.top";
        int year = LocalDate.now().getYear();
        String brand_name = "Ecommerce";
        Context context = new Context();
        context.setVariable("CODE", code);
        context.setVariable("BRAND_NAME", brand_name);
        context.setVariable("EXPIRE_MINUTES", expire);
        context.setVariable("YEAR", year);
        context.setVariable("VERIF_YURL", verifyUrl);
        String htmlContent = templateEngine.process("MailPage", context);

        //发送邮件
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(toEmail);
            helper.setSubject("【" + brand_name + "】邮箱验证码");
            helper.setText(htmlContent, true);
            mailSender.send(message);
            redisUtil.set(Constant.EMAIL_CAPTCHA_REDIS_KEY + toEmail, code, expire, TimeUnit.MINUTES);
            log.info("向{}成功发送邮件验证码{}", toEmail, code);
        } catch (MessagingException e) {
            log.error("邮件向:{}发送失败:{}", toEmail, e.getMessage());
        }
    }

    /**
     * 检查验证码已存在
     *
     * @param email 邮箱
     * @return false表示可以发送，true表示发送过于频繁
     */
    @Override
    public boolean canSendVerificationCode(String email) {
        return redisUtil.hasKey(Constant.EMAIL_CAPTCHA_REDIS_KEY + email);
    }

    /**
     * 获取邮箱
     *
     * @param email 邮箱
     * @return 邮箱
     */
    @Override
    public EmailEntity getEmail(String email) {
        return emailMapper.selectOne(new LambdaQueryWrapper<EmailEntity>()
                .eq(EmailEntity::getEmail, email));
    }

    /**
     * 校验邮件验证码
     */
    @Override
    public boolean verifyEmailCode(String email, int emailCode) {
        if (redisUtil.hasKey(Constant.EMAIL_CAPTCHA_REDIS_KEY + email)) {
            int code = (int) redisUtil.get(Constant.EMAIL_CAPTCHA_REDIS_KEY + email);
            if (code != emailCode) {
                return false;
            }
            redisUtil.del(Constant.EMAIL_CAPTCHA_REDIS_KEY + email);
            return true;
        }
        return false;
    }
}
