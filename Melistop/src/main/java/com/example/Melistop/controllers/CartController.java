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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private ProductService productService;
    private UserService userService;
    private CartService cartService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addToCart/{productId}")
    public String addToCart(@PathVariable Long productId, @RequestParam int quantity, RedirectAttributes redirectAttributes){
        // Lay thong tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> userOpt = userService.findByUsername(currentUsername);
        User user = userOpt.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng: " + currentUsername));

        // Lay thong tin san pham
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm: " + productId));

        // Them san pham vao gio hang
        cartService.addToCart(product, quantity, user);

        redirectAttributes.addFlashAttribute("message", "Đã thêm " + product.getName() + " với số lượng " + quantity + " vào giỏ hàng");
        // Sau khi them san pham thanh cong thi ve trang chu
        return "redirect:/";
    }

    //Hien thi gio hang
    @GetMapping
    public String showCart(Model model){
        // Lay thong tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> userOpt = userService.findByUsername(currentUsername);
        User user = userOpt.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng: " + currentUsername));

        model.addAttribute("cartItems", cartService.getCartItemsForUser(user));
        model.addAttribute("quantity",cartService.getCartItemsForUser(user).size());
        return "users/cart";
    }

    //Xoa don hang cua gio hang
    @PostMapping("/removeFromCart/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId, RedirectAttributes redirectAttributes){
        // Lay thong tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> userOpt = userService.findByUsername(currentUsername);
        User user = userOpt.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng: " + currentUsername));

        //Goi phuong thuc xoa san pham
        cartService.removeProductInCartOfUser(cartItemId, user);

        redirectAttributes.addFlashAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng");

        return "redirect:/cart";
    }
}
