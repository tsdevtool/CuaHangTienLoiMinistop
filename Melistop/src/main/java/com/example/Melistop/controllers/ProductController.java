package com.example.Melistop.controllers;
import com.example.Melistop.models.Image;
import com.example.Melistop.models.Product;
import com.example.Melistop.service.CategoryService;
import com.example.Melistop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products/product-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute Product product,
                             BindingResult result,
                             @RequestParam("avatarFile") MultipartFile avatarFile,
                             @RequestParam("imageFiles") MultipartFile[] imageFiles,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/products/add-product";
        }

        try {
            if (!avatarFile.isEmpty()) {
                String avatarFileName = saveFile(avatarFile);
                product.setAvatar(avatarFileName);
            }

            Product savedProduct = productService.addProduct(product);

            for (MultipartFile imageFile : imageFiles) {
                if (!imageFile.isEmpty()) {
                    String imageFileName = saveFile(imageFile);
                    Image image = new Image();
                    image.setUrl(imageFileName);
                    image.setProduct(savedProduct);
                    productService.addImage(image);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute Product product,
                                BindingResult result,
                                @RequestParam("avatarFile") MultipartFile avatarFile,
                                @RequestParam("imageFiles") MultipartFile[] imageFiles,
                                Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/products/update-product";
        }

        try {
            Product existingProduct = productService.getProductById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

            if (!avatarFile.isEmpty()) {
                String avatarFileName = saveFile(avatarFile);
                existingProduct.setAvatar(avatarFileName);
            }

            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setQuantity(product.getQuantity());

            Product updatedProduct = productService.updateProduct(existingProduct);

            for (MultipartFile imageFile : imageFiles) {
                if (!imageFile.isEmpty()) {
                    String imageFileName = saveFile(imageFile);
                    Image image = new Image();
                    image.setUrl(imageFileName);
                    image.setProduct(updatedProduct);
                    productService.addImage(image);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "admin/products/product-detail";
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get("uploads", fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return fileName;
    }


}
