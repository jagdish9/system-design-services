package com.sysdesign.audienceservice.repository;

import com.sysdesign.audienceservice.entity.Audience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienceRepository extends JpaRepository<Audience, Long> {
}
