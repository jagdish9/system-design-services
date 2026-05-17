package com.sysdesign.urlshortenerservice.controller;

import com.sysdesign.urlshortenerservice.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/url")
public class UrlShortenerController {

    private static final Logger log = LoggerFactory.getLogger(UrlShortenerController.class);

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(@RequestBody Map<String, String> request) {
        String longUrl = request.get("url");
        String shortUrl = urlShortenerService.shortenUrl(longUrl);
        return new ResponseEntity<>(Map.of("shortUrl", shortUrl), HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {
        String longUrl = urlShortenerService.longUrl(shortCode);
        return ResponseEntity.status(302).location(URI.create(longUrl)).build();
    }
}
