package com.example.Melistop.repository;


import com.example.Melistop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}
