package com.sysdesign.campaignservice.controller;

import com.sysdesign.campaignservice.entity.Campaign;
import com.sysdesign.campaignservice.service.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/create-campaign")
    public ResponseEntity<?> createCampaign(@RequestBody Campaign campaign) {
        return new ResponseEntity<>(campaignService.createCampaign(campaign), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCampaign(@PathVariable Long id) {
        return new ResponseEntity<>(campaignService.getCampaign(id), HttpStatus.OK);
    }
}
