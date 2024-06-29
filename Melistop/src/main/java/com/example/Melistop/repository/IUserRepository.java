package com.example.Melistop.repository;

import com.example.NguyenThanhSieu_9116.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
