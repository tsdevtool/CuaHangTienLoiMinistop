package com.example.Melistop.service;

import com.example.Melistop.DTO.RoleDTO;
import com.example.Melistop.models.Role;
import com.example.Melistop.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public List<RoleDTO> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RoleDTO findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Role not found"));
        return convertToDTO(role);
    }

    public RoleDTO findByName(String name) {
        Role role = roleRepository.findByName(name);
        return convertToDTO(role);
    }

    private RoleDTO convertToDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

    public Role convertToEntity(RoleDTO roleDTO) {
        return Role.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getName())
                .description(roleDTO.getDescription())
                .build();
    }
}
