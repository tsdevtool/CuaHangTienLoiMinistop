package com.example.Melistop.repository;

import com.example.NguyenThanhSieu_9116.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}
