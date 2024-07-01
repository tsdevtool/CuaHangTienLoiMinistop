package com.example.Melistop.controllers;

import com.example.Melistop.DTO.UserDTO;
import com.example.Melistop.models.User;
import com.example.Melistop.service.RoleService;
import com.example.Melistop.service.UserService;
import com.example.Melistop.service.UserManagementService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService; // Thêm khai báo userService

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userManagementService.findUsersByRole("USER"));
        return "admin/users/list";
    }

    @GetMapping("/add")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new User()); // Thêm một đối tượng User mới vào model
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/users/add";
    }

    @PostMapping("/add")
    public String register(@Valid @ModelAttribute("user") User user, // Validate đối tượng User
                           @NotNull BindingResult bindingResult, // Kết quả của quá trình validate
                           Model model) {
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "admin/users/add"; // Trả về lại view "register" nếu có lỗi
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("errors", "Mật khẩu không khớp");
            return "admin/users/add";
        }
        userService.save(user); // Lưu người dùng vào cơ sở dữ liệu
        userService.setDefaultRole(user.getUsername()); // Gán vai trò mặc định cho người dùng
        return "redirect:/admin/users"; // Chuyển hướng người dùng tới trang "login"
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        Optional<UserDTO> user = userManagementService.findUserById(id);
        if (user.isEmpty()) {
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user.get());
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/users/edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDTO userDTO, @RequestParam("roles") List<Long> roleIds, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAllRoles());
            return "admin/users/edit";
        }

        Set<Long> userRoles = new HashSet<>(roleIds);
        userDTO.setRoles(userRoles);

        if ((userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) || (userDTO.getConfirmPassword() != null && !userDTO.getConfirmPassword().isEmpty())) {
            if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                bindingResult.rejectValue("password", null, "Password and Confirm Password do not match");
                model.addAttribute("roles", roleService.findAllRoles());
                return "admin/users/edit";
            }
        } else {
            userDTO.setPassword(null);
            userDTO.setConfirmPassword(null);
        }

        try {
            userManagementService.updateUser(id, userDTO);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("password", null, e.getMessage());
            model.addAttribute("roles", roleService.findAllRoles());
            return "admin/users/edit";
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userManagementService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
