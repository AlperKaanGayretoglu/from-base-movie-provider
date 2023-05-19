package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Category;
import com.alpergayretoglu.movie_provider.model.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class MovieResponse {

    private String title;
    private Set<String> categories;

    public static MovieResponse fromEntity(Movie movie) {
        return new MovieResponse(
                movie.getTitle(),
                new HashSet<>(movie.getCategories().stream().map(Category::getName).collect(Collectors.toList()))
        );
    }
}
