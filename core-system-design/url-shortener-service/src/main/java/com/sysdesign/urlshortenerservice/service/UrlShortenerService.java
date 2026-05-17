package com.sysdesign.urlshortenerservice.service;

import com.sysdesign.urlshortenerservice.controller.UrlShortenerController;
import com.sysdesign.urlshortenerservice.entity.Url;
import com.sysdesign.urlshortenerservice.repository.UrlShortenerRepository;
import com.sysdesign.urlshortenerservice.util.Base62Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UrlShortenerService {

    private static final Logger log = LoggerFactory.getLogger(UrlShortenerService.class);

    private final UrlShortenerRepository urlShortenerRepository;

    public UrlShortenerService(UrlShortenerRepository urlShortenerRepository) {
        this.urlShortenerRepository = urlShortenerRepository;
    }

    @Value("${url.base-url}")
    private String baseUrl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String shortenUrl(String longUrl) {
        log.info("Processing url to shorten");
        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setCreatedAt(LocalDateTime.now());

        urlShortenerRepository.save(url);

        String shortCode = Base62Encoder.encode(url.getId());
        url.setShortCode(shortCode);

        urlShortenerRepository.save(url);

        //cache it
        redisTemplate.opsForValue().set(shortCode, longUrl, Duration.ofMinutes(30));
        log.info("Stored in cache for shortCode: {}", shortCode);

        return baseUrl + shortCode;
    }

    public String longUrl(String shortCode) {
        log.info("Processing shorten url to get long url");
        // 1. check Redis
        String longUrl = redisTemplate.opsForValue().get(shortCode);
        if(longUrl != null) {
            log.info("Cache hit!");
            return longUrl;
        } else {
            log.info("Cache miss!");
        }

        // 2. DB fallback
        Url url = urlShortenerRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Url not found"));

        // 3. Cache it again
        redisTemplate.opsForValue().set(shortCode, url.getLongUrl(), Duration.ofMinutes(30));

        return url.getLongUrl();
    }
}
