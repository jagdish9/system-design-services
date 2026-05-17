package com.sysdesign.audienceservice.service;

import com.sysdesign.audienceservice.entity.Audience;
import com.sysdesign.commonevents.dto.CampaignDto;
import com.sysdesign.commonevents.event.EmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, EmailEvent> kafkaTemplate;

    public void sendMessage(CampaignDto campaign, Audience audience) {
        EmailEvent event = new EmailEvent(
                campaign.getId(),
                audience.getId(),
                audience.getEmail(),
                campaign.getTemplate()
        );

        kafkaTemplate.send("campaign-email-topic", event);
    }
}
