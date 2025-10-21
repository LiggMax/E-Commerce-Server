//package com.ligg.entrance;
//
//import com.ligg.common.constants.OrderConstant;
//import com.ligg.common.constants.UserConstant;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @author Ligg
// * @Time 2025/10/21
// **/
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//public class OrderTest {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//
//    /**
//     * 添加订单缓存
//     */
//    @Test
//    public void addOrderCache() {
//        String orderNo = "dwoajnoawdkpaod_dpwoamdapdm";
//        String orderKey = UserConstant.USER_ID + ':' + OrderConstant.ORDER_KEY + orderNo;
//        redisTemplate.opsForValue().set(orderKey,"订单信息",5, TimeUnit.SECONDS);
//    }
//}
