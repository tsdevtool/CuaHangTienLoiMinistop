package com.example.Melistop.controllers;

import com.example.Melistop.models.Order;
import com.example.Melistop.models.Product;
import com.example.Melistop.models.User;
import com.example.Melistop.service.CategoryService;
import com.example.Melistop.service.OrderService;
import com.example.Melistop.service.ProductService;
import com.example.Melistop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService; // Đảm bảo bạn đã inject CategoryService
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

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
    @PostMapping("/products/{id}/updateQuantity")
    @ResponseBody
    public ResponseEntity<?> updateQuantity(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        int change = request.get("change");
        try {
            Product updatedProduct = productService.updateQuantity(id, change);
            return ResponseEntity.ok(updatedProduct.getQuantity());
        } catch (IllegalArgumentException e) {
            // Log lỗi chi tiết
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @GetMapping("/orders")
    public String showOrders(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username));
        List<Order> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "users/UserOrders"; // Tên của template HTML để hiển thị các đơn hàng của người dùng
    }

    @GetMapping("/profile/delete/{id}")
    public String deleteUserAccount(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        userService.deleteUserById(id);
        return "redirect:/login";
    }

}
