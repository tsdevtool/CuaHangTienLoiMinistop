package com.example.Melistop.controllers;

import com.example.Melistop.models.Product;
import com.example.Melistop.models.User;
import com.example.Melistop.service.CategoryService;
import com.example.Melistop.service.ProductService;

import com.example.Melistop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService; // Đảm bảo bạn đã inject CategoryService
    @Autowired
    private UserService userService;
    // Display a list of all products
    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/home";
    }
    @GetMapping("/search")
    public String searchProductsByName(@RequestParam("name") String name, Model model) {
        List<Product> searchResults = productService.findProductsByName(name);
        model.addAttribute("products", searchResults);
        return "admin/products/home"; // Template dùng cho người dùng
    }
    @RequestMapping("/403")
    public String accessDenied() {
        return "403"; // Trả về tên của file HTML chứa nội dung lỗi 403
    }

    @GetMapping("/products/detail/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "users/ProductDetail";
    }

    @GetMapping("/profile")
    public String showUserProfile(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username));
        model.addAttribute("user", user);
        return "users/UserProfile"; // Tên của template HTML chứa trang hồ sơ người dùng
    }

    @GetMapping("/products/by-category/{categoryId}")
    public String showProductsByCategory(@PathVariable Long categoryId, Model model) {
        List<Product> products = productService.findProductsByCategoryId(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/home"; // Tên của template HTML để hiển thị sản phẩm theo danh mục
    }

}
