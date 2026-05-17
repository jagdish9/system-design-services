package com.sysdesign.emailservice.service;

import com.sysdesign.commonevents.event.EmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "campaign-email-topic", groupId = "email-group")
    public void consume(EmailEvent event) {
        emailService.sendEmail(event);
    }
}
