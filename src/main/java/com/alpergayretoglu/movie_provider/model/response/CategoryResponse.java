package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Category;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse extends BaseResponse {

    private String name;
    private CategoryResponse parent;

    public static CategoryResponse fromEntity(Category category) {
        CategoryResponse response = CategoryResponse.builder()
                .name(category.getName())
                .parent(category.getParent() != null ? CategoryResponse.fromEntity(category.getParent()) : null)
                .build();

        return setCommonValuesFromEntity(response, category);
    }
}