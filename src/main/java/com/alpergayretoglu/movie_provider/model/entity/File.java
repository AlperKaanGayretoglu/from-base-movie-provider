package com.alpergayretoglu.movie_provider.model.entity;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File extends BaseEntity {

    private String url;

    private String name;

    // TODO: What is this? Is it MovieType OR FileType (I think FileType)
    private String contentType; // TODO: Should this be an enum ???

    private String byteSize;

}