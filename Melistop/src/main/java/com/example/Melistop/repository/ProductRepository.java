package com.example.Melistop.repository;

import com.example.Melistop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Tim cac san pham theo id cua category
    List<Product> findByCategoryId(Long categoryId);
}
