package com.example.Melistop.controllers;

import com.example.Melistop.DTO.UserDTO;
import com.example.Melistop.service.RoleService;
import com.example.Melistop.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeManagementController {
    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userManagementService.findUsersByRole("EMPLOYEE"));
        return "admin/employees/list";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/employees/add";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("user") UserDTO userDTO, @RequestParam("roles") List<Long> roleIds, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAllRoles());
            return "admin/employees/add";
        }
        Set<Long> userRoles = new HashSet<>(roleIds);
        userDTO.setRoles(userRoles);
        userManagementService.saveUser(userDTO);
        return "redirect:/admin/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        Optional<UserDTO> user = userManagementService.findUserById(id);
        if (user.isEmpty()) {
            return "redirect:/admin/employees";
        }
        model.addAttribute("user", user.get());
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/employees/edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDTO userDTO, @RequestParam("roles") List<Long> roleIds, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAllRoles());
            return "admin/employees/edit";
        }
        Set<Long> userRoles = new HashSet<>(roleIds);
        userDTO.setRoles(userRoles);
        userManagementService.updateUser(id, userDTO);
        return "redirect:/admin/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userManagementService.deleteUser(id);
        return "redirect:/admin/employees";
    }
}
