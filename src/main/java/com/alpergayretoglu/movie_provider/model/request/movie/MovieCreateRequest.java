package com.alpergayretoglu.movie_provider.model.request.movie;

import com.alpergayretoglu.movie_provider.model.entity.Movie;
import com.alpergayretoglu.movie_provider.repository.CategoryRepository;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
@Builder
public class MovieCreateRequest {

    private String title;
    private List<String> categoryNames;
    private String description;
    private int movieLength;

    public static Movie toEntity(MovieCreateRequest request, CategoryRepository categoryRepository) {
        return Movie.builder()
                .title(request.getTitle())
                .categories(new HashSet<>(categoryRepository.findAllByNameIn(request.getCategoryNames())))
                .description(request.getDescription())
                .movieLength(request.getMovieLength())
                .build();
    }

}