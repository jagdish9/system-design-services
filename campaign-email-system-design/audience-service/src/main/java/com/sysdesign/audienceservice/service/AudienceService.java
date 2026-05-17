package com.sysdesign.audienceservice.service;

import com.sysdesign.audienceservice.entity.Audience;
import com.sysdesign.audienceservice.repository.AudienceRepository;
import com.sysdesign.commonevents.dto.CampaignDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AudienceService {

    private final AudienceRepository audienceRepository;

    public AudienceService(AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Value("${schedule.batch-size}")
    private int BATCH_SIZE;

    public void processCampaign(CampaignDto campaign) {
        int page = 0;
        Page<Audience> audiencePage;

        do {
            audiencePage = audienceRepository.findAll(PageRequest.of(page, BATCH_SIZE));

            for(Audience audience : audiencePage.getContent()) {
                kafkaProducerService.sendMessage(campaign, audience);
            }

            page++;
        } while(audiencePage.hasNext());
    }
}
