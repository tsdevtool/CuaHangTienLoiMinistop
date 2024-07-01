package com.example.Melistop.controllers;

import com.example.Melistop.models.Product;
import com.example.Melistop.models.User;
import com.example.Melistop.service.CartService;
import com.example.Melistop.service.ProductService;
import com.example.Melistop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
//@RequestMapping("/cart")
public class CartController {

    private ProductService productService;
    private UserService userService;
    private CartService cartService;

    @PostMapping("/addToCart/{productId}/{quantity}")
    public String addToCart(@PathVariable Long productId, @PathVariable int quantity, RedirectAttributes redirectAttributes){
        //Lay thong tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();;
        Optional<User> userOpt = userService.findByUsername(currentUsername);
        User user = userOpt.get();

        //Lay thong tin san pham
        Product product = productService.getProductById(productId).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy sản phẩm: " + productId));

        // Kiểm tra số lượng yêu cầu không vượt quá số lượng hiện có của sản phẩm
        if (quantity > product.getQuantity()) {
            // Nếu số lượng yêu cầu lớn hơn số lượng hiện có, giảm số lượng xuống cận trên
            quantity = product.getQuantity();
            // Cập nhật thông báo lỗi
            redirectAttributes.addFlashAttribute("errorMessage", "Số lượng vượt quá số lượng hiện có. Chỉ được thêm vào giỏ hàng " + quantity + " sản phẩm.");
        }

        //Them san pham vao gio hang
        cartService.addToCart(product, quantity, user);

        //Sau khi them san pham thanh cong thi ve trang chu
        return "redirect:/";
    }


}
