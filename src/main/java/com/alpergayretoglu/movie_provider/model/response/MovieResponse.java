package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Category;
import com.alpergayretoglu.movie_provider.model.entity.Movie;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse extends BaseResponse {

    private String title;
    private Set<String> categories;

    public static MovieResponse fromEntity(Movie movie) {
        MovieResponse response = MovieResponse.builder()
                .title(movie.getTitle())
                .categories(new HashSet<>(movie.getCategories().stream().map(Category::getName).collect(Collectors.toList())))
                .build();

        return setCommonValuesFromEntity(response, movie);
    }
}
