package com.sysdesign.audienceservice.runner;

import com.sysdesign.audienceservice.entity.Audience;
import com.sysdesign.audienceservice.entity.AudienceMetadata;
import com.sysdesign.audienceservice.repository.AudienceMetadataRepository;
import com.sysdesign.audienceservice.repository.AudienceRepository;
import com.sysdesign.commonevents.dto.CampaignDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private AudienceMetadataRepository audienceMetadataRepository;

    @Override
    public void run(String... args) throws Exception {
        boolean alreadyRun = audienceMetadataRepository.findById("INIT_DONE").isPresent();

        List<Audience> audiences = audienceRepository.findAll();
        if(alreadyRun && !audiences.isEmpty()) {
            log.info("Skipping initialization...");
            return;
        }

        log.info("Starting loading fake data for testing");
        List<Audience> audienceList = new ArrayList<>();
        for(int i = 1; i <= 300; i++) {
            Audience audience = new Audience();
            audience.setEmail("user-"+i + "@hpark.com");
            audience.setName("user-"+i);
            audienceList.add(audience);
        }
        audienceRepository.saveAll(audienceList);
        log.info("Loaded fake data: {}", audienceList.size());
        if(!alreadyRun) {
            AudienceMetadata audienceMetadata = new AudienceMetadata();
            audienceMetadata.setKey("INIT_DONE");
            audienceMetadata.setValue(true);
            audienceMetadataRepository.save(audienceMetadata);
        }
    }
}
