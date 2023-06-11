package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.FileData;
import com.alpergayretoglu.movie_provider.model.enums.FileType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse extends BaseResponse {

    private String name;
    private String content; // TODO: change to byte[] or something else idk
    private FileType fileType;
    private long byteSize;

    public static FileResponse fromEntity(FileData file) {
        FileResponse response = FileResponse.builder()
                .name(file.getName())
                .content(file.getContent())
                .fileType(file.getFileType())
                .byteSize(file.getByteSize())
                .build();
        return setCommonValuesFromEntity(response, file);
    }
}