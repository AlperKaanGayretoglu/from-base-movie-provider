package com.alpergayretoglu.movie_provider.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Movie> movies = new HashSet<>();

    @ManyToMany(mappedBy = "followedCategories", fetch = FetchType.LAZY)
    private Set<User> followers;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    private boolean isSuperCategory;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Category> subCategories = new HashSet<>();

}