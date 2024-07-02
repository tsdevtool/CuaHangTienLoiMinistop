package com.example.Melistop.service;

import com.example.Melistop.repository.CategoryRepository;
import com.example.Melistop.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing categories.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Retrieve all categories from the database.
     * @return a list of categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieve a category by its id.
     * @param id the id of the category to retrieve
     * @return an Optional containing the found category or empty if not found
     */
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Add a new category to the database.
     * @param category the category to add
     */
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Update an existing category.
     * @param category the category with updated information
     */
    public void updateCategory(@NotNull Category category) {
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalStateException("Category with ID " +
                        category.getId() + " does not exist."));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    /**
     * Delete a category by its id.
     * @param id the id of the category to delete
     */
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new IllegalStateException("Category này không thể xóa, nó chứa dữ liệu của nhiều sản phẩm");
        }
        categoryRepository.deleteById(id);
    }
}
