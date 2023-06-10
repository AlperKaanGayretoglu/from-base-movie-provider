package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.model.entity.Category;
import com.alpergayretoglu.movie_provider.model.request.category.CategoryUpdateRequest;
import com.alpergayretoglu.movie_provider.model.response.CategoryResponse;
import com.alpergayretoglu.movie_provider.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    // TODO: Return the object instead of the Response counterpart for all functions

    private final CategoryRepository repository;


    // TODO: Can only add child category (so can't add a category that will be the parent of another one)
    public CategoryResponse addCategory(String parentId, String name) {
        // check parent if exists
        Category parent = repository.findById(parentId)
                .orElseThrow(EntityNotFoundException::new); // TODO: write a message here
        Category category = Category.builder()
                .name(name)
                .isSuperCategory(false)
                .parent(parent)
                .build();
        return CategoryResponse.fromEntity(repository.save(category));
    }

    public List<CategoryResponse> listCategories() {
        return repository.findAll().stream()
                .map(CategoryResponse::fromEntity).collect(Collectors.toList());
    }

    public Page<CategoryResponse> listCategories(Pageable pageable) {
        return repository.findAll(pageable).map(CategoryResponse::fromEntity);
    }

    public Category findCategoryById(String categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public CategoryResponse updateCategory(String id, CategoryUpdateRequest request) {
        Category category = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Category newParentCategory = repository.findCategoryByName(request.getParentName())
                .orElseThrow(EntityNotFoundException::new);

        if (category.getId().equals(newParentCategory.getId()))
            throw new RuntimeException("A category cannot be its own parent"); // TODO: instead of id, do name

        category.setName(request.getName());
        category.setParent(newParentCategory);
        return CategoryResponse.fromEntity(repository.save(category));
    }


    public void deleteCategory(String id) {
        Category category = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (category.isSuperCategory())
            throw new RuntimeException("Super category cannot be deleted");
        repository.deleteById(id);
    }
}