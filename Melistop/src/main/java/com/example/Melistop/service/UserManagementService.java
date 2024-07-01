package com.example.Melistop.service;

import com.example.Melistop.DTO.UserDTO;
import com.example.Melistop.models.Role;
import com.example.Melistop.models.User;
import com.example.Melistop.repository.RoleRepository;
import com.example.Melistop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<UserDTO> findUsersByRole(String roleName) {
        List<User> users = userRepository.findByRoleName(roleName);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<UserDTO> findUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.map(this::convertToDTO);
    }

    public void saveUser(UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }
        User user = convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void updateUser(Long id, UserDTO userDTO) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setName(userDTO.getName());
            existingUser.setProvider(userDTO.getProvider());
            existingUser.setLocked(userDTO.isLocked());

            // Kiểm tra nếu mật khẩu mới được cung cấp
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                    throw new IllegalArgumentException("Password and Confirm Password do not match");
                }
                existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                existingUser.setConfirmPassword(passwordEncoder.encode(userDTO.getConfirmPassword()));
            }

            Set<Role> roles = userDTO.getRoles().stream()
                    .map(roleId -> roleRepository.findById(roleId).orElseThrow(() -> new IllegalStateException("Role not found")))
                    .collect(Collectors.toSet());
            existingUser.setRoles(roles);
            userRepository.save(existingUser);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setProvider(user.getProvider());
        userDTO.setRoles(user.getRoles().stream().map(Role::getId).collect(Collectors.toSet()));
        userDTO.setLocked(user.isLocked());
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setProvider(userDTO.getProvider());
        user.setLocked(userDTO.isLocked());
        user.setRoles(userDTO.getRoles().stream()
                .map(roleId -> roleRepository.findById(roleId).orElseThrow(() -> new IllegalStateException("Role not found")))
                .collect(Collectors.toSet()));
        return user;
    }
}
