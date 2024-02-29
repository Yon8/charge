package com.lxy.charge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@SpringBootTest
class ChargeApplicationTests {
@Autowired
private RedisTemplate<String,Object> redisTemplate;
    @Test
    void contextLoads() {
        String cacheKeyPattern = "wardenListCacheKey*";

        Set<String> cacheKeys = redisTemplate.keys(cacheKeyPattern);
        redisTemplate.delete(cacheKeys);
    }

}
