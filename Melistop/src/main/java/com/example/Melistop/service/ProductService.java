package com.example.Melistop.service;

import com.example.NguyenThanhSieu_9116.model.OrderDetail;
import com.example.NguyenThanhSieu_9116.model.Product;
import com.example.NguyenThanhSieu_9116.repository.OrderDetailRepository;
import com.example.NguyenThanhSieu_9116.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

//    public Optional<Product> findById(Long id){
//        return productRepository.findById(id);
//    }
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(@NotNull Product product){
        Product existingProduct = productRepository.findById(product.getId()).orElseThrow(() -> new IllegalStateException("Product with ID " + product.getId() + " does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImage(product.getImage());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setCategory(product.getCategory());
        return productRepository.save(existingProduct);
    }

    public void deleteProductById(Long id){
//        if(!productRepository.existsById(id)){
//            throw new IllegalStateException("Product with ID " + id + " does not exist.");
//        }

        Product product= productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product with ID " + id + " does not exist."));

        List<OrderDetail> orderDetails = orderDetailRepository.findByProductId(id);
        for(OrderDetail orderDetail: orderDetails){
            orderDetail.setProduct(null);
            orderDetailRepository.save(orderDetail);
        }
        productRepository.deleteById(id);
    }

}
