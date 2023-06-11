package com.alpergayretoglu.movie_provider.model.entity;

import com.alpergayretoglu.movie_provider.model.enums.FileType;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileData extends BaseEntity {

    private String name;

    private String content; // TODO: change to byte[] or something else idk

    private FileType fileType;

    private long byteSize;

}