package org.szy;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisConnectionTest {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void testRedis() {
        System.out.println(stringRedisTemplate);
        boolean res = stringRedisTemplate.opsForValue().setIfAbsent("test20 from spring", "val from spring");
        System.out.println(res);
    }
}
