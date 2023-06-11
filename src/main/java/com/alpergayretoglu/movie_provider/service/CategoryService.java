package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.model.entity.Category;
import com.alpergayretoglu.movie_provider.model.request.category.CategoryUpdateRequest;
import com.alpergayretoglu.movie_provider.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository repository;

    public Category addCategory(String parentCategoryId, String name) {
        Category parent = findCategoryById(parentCategoryId);

        Category category = Category.builder()
                .name(name)
                .isSuperCategory(false)
                .parent(parent)
                .build();

        return repository.save(category);
    }

    public Page<Category> listCategories(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Category findCategoryById(String categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId + "."));
    }

    public Category findCategoryByName(String categoryName) {
        return repository.findCategoryByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + categoryName + "."));
    }

    public Category updateCategory(String categoryId, CategoryUpdateRequest request) {
        Category category = findCategoryById(categoryId);

        if (category.isSuperCategory()) {
            throw new RuntimeException("Super category cannot be updated");
        }

        Category newParentCategory = findCategoryByName(request.getParentName());

        if (category.getId().equals(newParentCategory.getId())) {
            throw new RuntimeException("A category cannot be its own parent");
        }

        category.setName(request.getName());
        category.setParent(newParentCategory);
        return repository.save(category);
    }

    public void deleteCategory(String categoryId) {
        Category category = findCategoryById(categoryId);

        if (category.isSuperCategory()) {
            throw new RuntimeException("Super category cannot be deleted");
        }

        // assign all subcategories to grandparent
        Category grandParent = category.getParent();
        for (Category subCategory : category.getSubCategories()) {
            subCategory.setParent(grandParent);
        }

        repository.delete(category);
    }

}