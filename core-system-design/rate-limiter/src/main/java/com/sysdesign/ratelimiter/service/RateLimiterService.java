package com.sysdesign.ratelimiter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final int LIMIT = 10;
    private static final int WINDOW = 60;

    public boolean allowRequest(String userId) {
        //current time window (minute based)
        long currentWindow = System.currentTimeMillis() / 1000 / WINDOW;
        log.info("currentWindow: {}", currentWindow);

        String key = "rate_limit:" + userId + ":" + currentWindow;
        log.info("key: {}", key);

        //Atomic increment
        Long count = redisTemplate.opsForValue().increment(key);
        log.info("count: {}", count);

        if(count == null) {
            return false;
        }

        //set expiry only for first request
        if(count == 1) {
            redisTemplate.expire(key, WINDOW, TimeUnit.SECONDS);
        }

        log.info("count: {} and limit: {}", count, LIMIT);

        //check limit
        return count <= LIMIT;
    }
}
