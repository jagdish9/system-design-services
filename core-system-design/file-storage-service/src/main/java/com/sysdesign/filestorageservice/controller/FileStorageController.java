package com.sysdesign.filestorageservice.controller;

import com.sysdesign.filestorageservice.entity.FileMetadata;
import com.sysdesign.filestorageservice.service.FileStorageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    //Get upload url
    @PostMapping("/upload-url")
    public String uploadUrl(@RequestParam String fileName,
                            @RequestParam String contentType) {
        return fileStorageService.generateUploadUrl(fileName, contentType);
    }

    //Save metadata
    @PostMapping("/metadata")
    public FileMetadata saveMetadata(@RequestBody FileMetadata fileMetadata) {
        return fileStorageService.saveMetadata(fileMetadata);
    }

    //Download
    @GetMapping("/{id}/download")
    public String download(@PathVariable UUID id) {
        return fileStorageService.generateDownloadUrl(id);
    }

    @GetMapping("/list-files")
    public List<FileMetadata> listFiles() {
        return fileStorageService.listFiles();
    }
}
