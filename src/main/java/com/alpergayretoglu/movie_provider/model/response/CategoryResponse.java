package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Category;
import com.alpergayretoglu.movie_provider.model.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class CategoryResponse {

    private String name;
    private boolean isSuperCategory;
    private List<String> movies;

    public static CategoryResponse fromEntity(Category category) {
        return new CategoryResponse(
                category.getName(),
                category.isSuperCategory(),
                category.getMovies().stream().map(Movie::getTitle).collect(Collectors.toList())
        );
    }
}