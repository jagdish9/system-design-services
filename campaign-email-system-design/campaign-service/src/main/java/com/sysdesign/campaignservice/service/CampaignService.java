package com.sysdesign.campaignservice.service;

import com.sysdesign.commonevents.constants.CampaignStatus;
import com.sysdesign.campaignservice.entity.Campaign;
import com.sysdesign.campaignservice.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public Campaign createCampaign(Campaign campaign) {
        campaign.setStatus(CampaignStatus.CREATED.name());
        campaign.setStartTime(LocalDateTime.now());
        return campaignRepository.save(campaign);
    }

    public Campaign getCampaign(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found "+ id));
    }
}
