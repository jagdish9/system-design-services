package com.sysdesign.ratelimiter.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RateLimiterServiceUsingLuaScript {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterServiceUsingLuaScript.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private DefaultRedisScript<Long> script;

    @PostConstruct
    public void init() {
        script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/rate_limiter.lua"));
        script.setResultType(Long.class);
    }

    public boolean allowRequest(String userId) {

        String key = "rate_limit:" + userId;

        int capacity = 10; //max tokens
        int refillRate = 1; //tokens per second
        int requested = 1;

        long currentTime = System.currentTimeMillis();
        log.info("current time in mills: {}", currentTime);
        long now = currentTime / 1000;

        log.info("key: {}, capacity: {}, refill_rate: {}, now: {}, requested: {}",
                key, capacity, refillRate, now, requested);
        Long result = redisTemplate.execute(
                script,
                Collections.singletonList(key),
                String.valueOf(capacity),
                String.valueOf(refillRate),
                String.valueOf(now),
                String.valueOf(requested)
        );

        return result == 1;
    }
}
