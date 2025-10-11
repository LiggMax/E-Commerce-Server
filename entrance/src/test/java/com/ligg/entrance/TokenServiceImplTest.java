//package com.ligg.entrance;
//
//import com.ligg.common.module.entity.UserEntity;
//import com.ligg.common.service.impl.TokenServiceImpl;
//import com.ligg.common.utils.JWTUtil;
//import com.ligg.common.utils.RedisUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.ligg.common.constants.Constant.EXPIRE;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
///**
// * @Author Ligg
// * @Time 2025/9/28
// **/
//@Slf4j
//@SpringBootTest
//public class TokenServiceImplTest {
//
//    @Mock
//    private RedisUtil redisUtil;
//
//    @InjectMocks
//    private TokenServiceImpl tokenService;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        // 初始化模拟对象
//        MockitoAnnotations.openMocks(this);
//        log.info("开始初始化TokenServiceImplTest测试类");
//    }
//
//    @Test
//    void generateToken() {
//        log.info("开始执行generateToken测试");
//        // 准备测试数据
//        UserEntity userEntity = new UserEntity();
//        String userId = "user123";
//        String account = "testAccount";
//        userEntity.setUserId(userId);
//        userEntity.setAccount(account);
//        log.debug("创建测试用户实体: userId={}, account={}", userId, account);
//        Map<String, Object> userInfo = new HashMap<>();
//        userInfo.put("userId", userId);
//        userInfo.put("account", account);
//        log.debug("构建用户信息映射: {}", userInfo);
//
//        String expectedToken = "generatedToken123";
//
//        try (MockedStatic<JWTUtil> mockedJWTUtil = mockStatic(JWTUtil.class)) {
//            // 设置mock行为
//            mockedJWTUtil.when(() -> JWTUtil.createToken(userInfo, EXPIRE)).thenReturn(expectedToken);
//            log.debug("设置JWTUtil mock行为，当调用createToken时返回: {}", expectedToken);
//
//            // 执行测试
//            String actualToken = tokenService.generateToken(userEntity);
//            log.info("generateToken方法执行完成，返回token: {}", actualToken);
//
//            // 验证结果
//            assertEquals(expectedToken, actualToken);
//            mockedJWTUtil.verify(() -> JWTUtil.createToken(userInfo, EXPIRE), times(1));
//            log.info("generateToken测试验证通过");
//        }
//    }
//
//    @Test
//    void saveToken() {
//        log.info("开始执行saveToken测试");
//        // 准备测试数据
//        String token = "testToken123";
//        String userId = "user123";
//        log.debug("准备测试数据: token={}, userId={}, expireTime={}", token, userId, EXPIRE);
//
//        // 设置mock行为
//        when(redisUtil.set("Token:" + userId, token, EXPIRE)).thenReturn(true);
//        log.debug("设置RedisUtil mock行为，当调用set方法时返回true");
//
//        // 执行测试
//        tokenService.saveToken(token, userId);
//        log.info("saveToken方法执行完成");
//
//        // 验证结果
//        verify(redisUtil, times(1)).set("Token:" + userId, token, EXPIRE);
//        log.info("saveToken测试验证通过，确认redisUtil.set方法被正确调用");
//    }
//}
