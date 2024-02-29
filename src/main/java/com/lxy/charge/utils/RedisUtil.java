package com.lxy.charge.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 存储数据到 Redis，并设置过期时间（单位：秒）
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    // 获取 Redis 中存储的数据
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 判断 Redis 中是否存在某个 key
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    // 删除 Redis 中的某个 key
    public void delete(String key) {
        redisTemplate.delete(key);
    }
    // 删除 Redis 中所有相关联的 仅差最后一个字的Key （分页需要）
    public void deleteAllRelatedCache(String cacheKey) {
        String cacheKeyPattern = cacheKey + "*";
        Set<String> cacheKeys = redisTemplate.keys(cacheKeyPattern);
        if (cacheKeys != null) {
            redisTemplate.delete(cacheKeys);
        }
    }

    // 自增操作
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

}
