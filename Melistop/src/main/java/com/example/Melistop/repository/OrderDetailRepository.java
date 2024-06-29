package com.example.Melistop.repository;

import com.example.NguyenThanhSieu_9116.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  java.util.*;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByProductId(Long productId);
}
