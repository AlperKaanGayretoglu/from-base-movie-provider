package com.alpergayretoglu.movie_provider.model.request.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryCreateRequest {
    private String name;
}