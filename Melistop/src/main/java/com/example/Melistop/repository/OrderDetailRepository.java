package com.example.Melistop.repository;

import com.example.Melistop.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    boolean existsByProductId(Long productId);
}
