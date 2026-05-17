package com.sysdesign.filestorageservice.service;

import com.sysdesign.filestorageservice.entity.FileMetadata;
import com.sysdesign.filestorageservice.repository.FileStorageRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final S3Presigner s3Presigner;

    public FileStorageService(FileStorageRepository fileStorageRepository, S3Presigner s3Presigner) {
        this.fileStorageRepository = fileStorageRepository;
        this.s3Presigner = s3Presigner;
    }

    private final String bucketName = "s3-1-demo1-bucket1"; //your bucket name

    public String generateUploadUrl(String fileName, String contentType) {
        String key = UUID.randomUUID() + "_" + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedPutObjectRequest =
                s3Presigner.presignPutObject(presignRequest);

        return presignedPutObjectRequest.url().toString();
    }

    public FileMetadata saveMetadata(FileMetadata fileMetadata) {
        fileMetadata.setOwner("user1");
        fileMetadata.setCreatedAt(LocalDateTime.now());

        return fileStorageRepository.save(fileMetadata);
    }

    public String generateDownloadUrl(UUID id) {
        FileMetadata file = fileStorageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getS3Key())
                .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();

        return s3Presigner.presignGetObject(presignRequest)
                .url()
                .toString();
    }

    public List<FileMetadata> listFiles() {
        return fileStorageRepository.findAll();
    }
}
