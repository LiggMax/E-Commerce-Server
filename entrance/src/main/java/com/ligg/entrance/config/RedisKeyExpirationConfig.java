/**
 * @author Ligg
 * @Time 2025/10/21
 **/
package com.ligg.entrance.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * Redis key 过期通知
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisKeyExpirationConfig implements CommandLineRunner {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        if (connectionFactory != null) {
            connectionFactory.getConnection()
                    .serverCommands().setConfig("notify-keyspace-events", "Ex");
            log.info("Redis key过期通知已启用");
        }
    }
}
