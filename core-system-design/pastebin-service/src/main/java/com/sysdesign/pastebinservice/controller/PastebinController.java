package com.sysdesign.pastebinservice.controller;

import com.sysdesign.pastebinservice.service.PastebinService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paste")
public class PastebinController {

    private final PastebinService pastebinService;

    public PastebinController(PastebinService pastebinService) {
        this.pastebinService = pastebinService;
    }

    @Value("${url.base-url}")
    private String url;

    @PostMapping
    public String createPaste(@RequestBody String content,
                              @RequestParam(required = false, defaultValue = "0") long ttlMills) {
        String id = pastebinService.createPaste(content, ttlMills);
        return url + "paste/" + id;
    }

    @GetMapping("/{id}")
    public String getPaste(@PathVariable String id) {
        return pastebinService.getPaste(id);
    }
}
