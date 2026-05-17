package com.sysdesign.pastebinservice.repository;

import com.sysdesign.pastebinservice.entity.Paste;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PastebinRepository {
    private static final Logger log = LoggerFactory.getLogger(PastebinRepository.class);

    private final ConcurrentHashMap<String, Paste> store = new ConcurrentHashMap<>();

    public void save(Paste paste) {
        log.info("Storing into in-memory DB");
        store.put(paste.getId(), paste);
    }

    public Paste findById(String id) {
        log.info("Searching into in-memory DB");
        return store.get(id);
    }

    public void delete(String id) {
        log.info("Deleting from in-memory DB");
        store.remove(id);
    }
}

/*
Repository (In-Memory)
 */