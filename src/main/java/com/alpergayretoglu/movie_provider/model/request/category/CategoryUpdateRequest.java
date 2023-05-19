package com.alpergayretoglu.movie_provider.model.request.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryUpdateRequest {
    private String name;
    private String parentName;
}
