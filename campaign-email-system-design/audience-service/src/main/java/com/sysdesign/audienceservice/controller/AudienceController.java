package com.sysdesign.audienceservice.controller;

import com.sysdesign.audienceservice.service.AudienceService;
import com.sysdesign.commonevents.dto.CampaignDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audience")
public class AudienceController {

    private final AudienceService audienceService;

    public AudienceController(AudienceService audienceService) {
        this.audienceService = audienceService;
    }

    @PostMapping
    public String processCampaign(@RequestBody CampaignDto campaign) {
        audienceService.processCampaign(campaign);
        return "Campaign processed successfully";
    }
}
