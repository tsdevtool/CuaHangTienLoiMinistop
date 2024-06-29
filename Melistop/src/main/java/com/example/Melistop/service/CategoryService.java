package com.example.Melistop.service;

import com.example.NguyenThanhSieu_9116.model.Category;
import com.example.NguyenThanhSieu_9116.model.OrderDetail;
import com.example.NguyenThanhSieu_9116.model.Product;
import com.example.NguyenThanhSieu_9116.repository.CategoryRepository;
import com.example.NguyenThanhSieu_9116.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id){
        return  categoryRepository.findById(id);
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void updateCategory(@NotNull Category category){
        Category existingCategory = categoryRepository.findById(category.getId()).orElseThrow(() -> new IllegalStateException("Category with ID " + category.getId() + " does not exist"));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    public void deleteCategoryById(Long id){
//        if(!categoryRepository.existsById(id))
//            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        Category category= categoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("Category with ID " + id + " does not exist."));

        List<Product> products = productRepository.findByCategoryId(id);
        for(Product product: products){
            product.setCategory(null);
            productRepository.save(product);
        }
        categoryRepository.deleteById(id);
    }
}
