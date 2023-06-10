package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Category;
import com.alpergayretoglu.movie_provider.model.entity.Movie;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse extends BaseResponse {

    private String name;
    private boolean isSuperCategory;
    private List<String> movies;

    public static CategoryResponse fromEntity(Category category) {
        CategoryResponse response = CategoryResponse.builder()
                .name(category.getName())
                .isSuperCategory(category.isSuperCategory())
                .movies(category.getMovies().stream().map(Movie::getTitle).collect(Collectors.toList()))
                .build();

        return setCommonValuesFromEntity(response, category);
    }
}