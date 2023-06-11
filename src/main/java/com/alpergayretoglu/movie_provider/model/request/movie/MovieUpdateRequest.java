package com.alpergayretoglu.movie_provider.model.request.movie;

import com.alpergayretoglu.movie_provider.model.entity.Movie;
import com.alpergayretoglu.movie_provider.repository.CategoryRepository;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
@Builder
public class MovieUpdateRequest {

    private String title;
    private List<String> categoryNames;
    private String description;
    private int movieLength;

    public static Movie updateWith(Movie movie, MovieUpdateRequest request, CategoryRepository categoryRepository) {
        movie.setTitle(request.getTitle());
        movie.setCategories(new HashSet<>(categoryRepository.findAllByNameIn(request.getCategoryNames())));
        movie.setDescription(request.getDescription());
        movie.setMovieLength(request.getMovieLength());
        return movie;
    }

}
