package com.sysdesign.urlshortenerservice.repository;

import com.sysdesign.urlshortenerservice.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlShortenerRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortCode(String shortCode);
}
