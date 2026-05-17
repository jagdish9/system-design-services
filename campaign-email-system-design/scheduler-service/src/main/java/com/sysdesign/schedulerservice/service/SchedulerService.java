package com.sysdesign.schedulerservice.service;

import com.sysdesign.commonevents.dto.CampaignDto;
import com.sysdesign.commonevents.constants.CampaignStatus;
import com.sysdesign.schedulerservice.entity.Campaign;
import com.sysdesign.schedulerservice.repository.CampaignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private CampaignRepository campaignRepository;

    @Autowired
    public void setCampaignRepository(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Value("${audience.service.url}")
    private String url;

    @Scheduled(fixedRate = 60000) // run every minute
    public void runCampaigns() {
        List<Campaign> campaignList = campaignRepository.findAll();
        for(Campaign campaign : campaignList) {
            if(CampaignStatus.CREATED.name().equals(campaign.getStatus())
            && campaign.getStartTime().isBefore(LocalDateTime.now())) {
                campaign.setStatus(CampaignStatus.RUNNING.name());
                campaignRepository.save(campaign);

                CampaignDto campaignDto = new CampaignDto(campaign.getId(), campaign.getName(),
                        campaign.getStartTime(), campaign.getQuery(),
                        campaign.getTemplate(), campaign.getStatus());

                //calling audience service using rest template, sync call
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<CampaignDto> entity = new HttpEntity<>(campaignDto, headers);
                ResponseEntity<String> response = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

                log.info("Campaign process output: {}", response.getBody());
            }
        }
    }
}
