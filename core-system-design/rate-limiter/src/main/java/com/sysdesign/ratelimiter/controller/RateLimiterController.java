package com.sysdesign.ratelimiter.controller;

import com.sysdesign.ratelimiter.service.RateLimiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RateLimiterController {

    private RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/resource")
    public ResponseEntity<?> accessResource(@RequestParam String userId) {
        boolean allowed = rateLimiterService.allowRequest(userId);

        if(!allowed) {
            return ResponseEntity.status(429)
                    .body("Rate limit exceeded");
        }

        return ResponseEntity.ok("Request successful");
    }
}
