package com.example.Melistop.service;

import com.example.Melistop.models.Role;
import com.example.Melistop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving roles", e);
        }
    }
}
