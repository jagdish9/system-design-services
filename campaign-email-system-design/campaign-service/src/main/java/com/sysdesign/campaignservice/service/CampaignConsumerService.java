package com.sysdesign.campaignservice.service;

import com.sysdesign.campaignservice.entity.Campaign;
import com.sysdesign.campaignservice.repository.CampaignRepository;
import com.sysdesign.commonevents.event.CampaignStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CampaignConsumerService {

    private static final Logger log = LoggerFactory.getLogger(CampaignConsumerService.class);

    @Autowired
    private CampaignRepository campaignRepository;

    @KafkaListener(topics = "campaign-status-topic", groupId = "campaign-group")
    public void updateCampaignStatus(CampaignStatusEvent campaignStatus) {
        log.info("Updating campaign status");
        Campaign existingCampaign = campaignRepository.findById(campaignStatus.getCampaignId())
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        if(existingCampaign != null) {
            existingCampaign.setStatus(campaignStatus.getStatus());
            campaignRepository.save(existingCampaign);
        }
    }
}
