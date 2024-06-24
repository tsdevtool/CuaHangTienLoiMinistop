package com.example.Melistop.service;

import com.example.Melistop.models.Product;
import com.example.Melistop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    //Lay danh sach tat ca cac san pham
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //Lay danh sach tat ca san pham theo Category id
    public List<Product> getAllProductByCategoryId(Long id){
        return productRepository.findByCategoryId(id);
    }

    //Lay thong tin san pham theo id
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

}
