package com.sysdesign.audienceservice.repository;

import com.sysdesign.audienceservice.entity.AudienceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienceMetadataRepository extends JpaRepository<AudienceMetadata, String> {
}
