package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.request.category.CategoryCreateRequest;
import com.alpergayretoglu.movie_provider.model.request.category.CategoryUpdateRequest;
import com.alpergayretoglu.movie_provider.model.response.CategoryResponse;
import com.alpergayretoglu.movie_provider.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> listCategories() {
        return categoryService.listCategories();
    }

    @PostMapping("{parentId}")
    public CategoryResponse addCategory(@PathVariable String parentId, @RequestBody CategoryCreateRequest request) {
        return categoryService.addCategory(parentId, request.getName());
    }

    @GetMapping("{id}")
    public CategoryResponse singleCategory(@PathVariable String id) {
        return CategoryResponse.fromEntity(categoryService.findCategoryById(id));
    }

    @PutMapping("{id}")
    public CategoryResponse updateCategory(@PathVariable String id, @RequestBody CategoryUpdateRequest request) {
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }
}