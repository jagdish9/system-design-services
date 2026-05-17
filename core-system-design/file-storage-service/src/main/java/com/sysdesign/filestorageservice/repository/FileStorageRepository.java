package com.sysdesign.filestorageservice.repository;

import com.sysdesign.filestorageservice.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileStorageRepository extends JpaRepository<FileMetadata, UUID> {

}
