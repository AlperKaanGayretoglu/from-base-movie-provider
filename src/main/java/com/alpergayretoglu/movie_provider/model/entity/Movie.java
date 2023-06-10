package com.alpergayretoglu.movie_provider.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends BaseEntity {

    private String title;

    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("movies")
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    // TODO: Implement this
//    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY)
//    private Set<User> fans = new HashSet<>();

    private String description;

    private int movieLength;

}