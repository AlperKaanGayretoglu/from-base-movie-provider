package com.alpergayretoglu.movie_provider.model.request.file;

import com.alpergayretoglu.movie_provider.model.entity.FileData;
import com.alpergayretoglu.movie_provider.model.enums.FileType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadRequest {

    private String name;
    private String content; // TODO: change to byte[] or something else idk

    public static FileData toEntity(FileUploadRequest request) {
        return FileData.builder()
                .name(request.getName())
                .content(request.getContent())
                .fileType(FileType.STRING)
                .byteSize(request.getContent().getBytes().length)
                .build();
    }
}
