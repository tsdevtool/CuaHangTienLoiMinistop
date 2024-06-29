package com.example.Melistop.repository;

import com.example.NguyenThanhSieu_9116.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
