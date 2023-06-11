package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.model.entity.FileData;
import com.alpergayretoglu.movie_provider.model.request.file.FileUploadRequest;
import com.alpergayretoglu.movie_provider.repository.FileDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    
    private final FileDataRepository fileDataRepository;

    public FileData storeFile(FileUploadRequest file) {
        FileData fileData = FileUploadRequest.toEntity(file);
        return fileDataRepository.save(fileData);
    }

    public FileData getFileById(String fileId) {
        return fileDataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
    }

    public List<FileData> listFiles() {
        return fileDataRepository.findAll();
    }
}