package com.sysdesign.pastebinservice.service;

import com.sysdesign.pastebinservice.entity.Paste;
import com.sysdesign.pastebinservice.exception.ResourceNotFoundException;
import com.sysdesign.pastebinservice.repository.PastebinRepository;
import com.sysdesign.pastebinservice.util.Base62Generator;
import org.springframework.stereotype.Service;

@Service
public class PastebinService {

    private final PastebinRepository pastebinRepository;

    public PastebinService(PastebinRepository pastebinRepository) {
        this.pastebinRepository = pastebinRepository;
    }

    public String createPaste(String content, long ttlMills) {
        String id = Base62Generator.generateId();

        long expiry = ttlMills > 0 ? System.currentTimeMillis() + ttlMills : 0;

        Paste paste = new Paste(id, content, expiry);

        pastebinRepository.save(paste);

        return id;
    }

    public String getPaste(String id) {
        Paste paste = pastebinRepository.findById(id);

        if(paste == null) {
            throw new ResourceNotFoundException("Paste not found");
        }

        if(paste.isExpired()) {
            pastebinRepository.delete(id);
            throw new ResourceNotFoundException("Paste expired");
        }

        return paste.getContent();
    }
}
