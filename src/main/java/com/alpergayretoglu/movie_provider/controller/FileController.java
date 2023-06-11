package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.request.file.FileUploadRequest;
import com.alpergayretoglu.movie_provider.model.response.FileResponse;
import com.alpergayretoglu.movie_provider.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {

    private FileService fileService;

    @GetMapping
    public List<FileResponse> listFiles() {
        return fileService.listFiles().stream().map(FileResponse::fromEntity).collect(Collectors.toList());
    }

    @PostMapping("upload")
    public FileResponse uploadFile(@RequestBody FileUploadRequest file) {
        return FileResponse.fromEntity(fileService.storeFile(file));
    }

    @GetMapping("download/{fileId}")
    public FileResponse downloadFile(@PathVariable String fileId) {
        return FileResponse.fromEntity(fileService.getFileById(fileId));
    }
}