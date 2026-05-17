package com.sysdesign.emailservice.service;

import com.sysdesign.commonevents.event.CampaignStatusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, CampaignStatusEvent> kafkaTemplate;

    public void sendCampaignStatus(CampaignStatusEvent campaignStatus) {
        kafkaTemplate.send("campaign-status-topic", campaignStatus);
    }
}
