package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.request.category.CategoryCreateRequest;
import com.alpergayretoglu.movie_provider.model.request.category.CategoryUpdateRequest;
import com.alpergayretoglu.movie_provider.model.response.CategoryResponse;
import com.alpergayretoglu.movie_provider.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @ApiPageable
    public Page<CategoryResponse> listCategories(@ApiIgnore Pageable pageable) {
        return categoryService.listCategories(pageable);
    }

    @PostMapping("{parentId}")
    public CategoryResponse addCategory(@PathVariable String parentId, @RequestBody CategoryCreateRequest request) {
        return CategoryResponse.fromEntity(categoryService.addCategory(parentId, request.getName()));
    }

    @GetMapping("{id}")
    public CategoryResponse singleCategory(@PathVariable String id) {
        return CategoryResponse.fromEntity(categoryService.findCategoryById(id));
    }

    @PutMapping("{id}")
    public CategoryResponse updateCategory(@PathVariable String id, @RequestBody CategoryUpdateRequest request) {
        return CategoryResponse.fromEntity(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }
}