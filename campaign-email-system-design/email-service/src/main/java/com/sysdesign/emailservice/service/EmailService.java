package com.sysdesign.emailservice.service;

import com.sysdesign.commonevents.constants.CampaignStatus;
import com.sysdesign.commonevents.event.CampaignStatusEvent;
import com.sysdesign.commonevents.event.EmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public void sendEmail(EmailEvent event) {
        try {
            log.info("Sending email to: {}", event.getEmail());

            // call SMTP / SendGrid here
        } catch (Exception e) {
            // retry logic and DLQ
        }

        CampaignStatusEvent campaignStatus = new CampaignStatusEvent(event.getCampaignId(), CampaignStatus.COMPLETED.name());
        kafkaProducerService.sendCampaignStatus(campaignStatus);
    }
}
