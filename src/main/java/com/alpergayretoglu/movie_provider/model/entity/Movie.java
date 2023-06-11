package com.alpergayretoglu.movie_provider.model.entity;

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
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(mappedBy = "favoriteMovies", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<User> fans = new HashSet<>();

    private String description;

    private int movieLength;

}