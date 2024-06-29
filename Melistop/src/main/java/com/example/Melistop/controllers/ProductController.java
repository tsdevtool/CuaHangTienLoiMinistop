package com.example.Melistop.controllers;

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
@RequestMapping("/")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showProductList(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "products/products-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/products/add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult result, @RequestParam("imageFile")MultipartFile imageFile, Model model){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/products/add-product";
//            return "/products/add-product";
        }
        try {
            if (!imageFile.isEmpty()) {
                String imageFileName = saveFile(imageFile);
                product.setImage(imageFileName);
            }

            productService.addProduct(product);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile, Model model){
        if(result.hasErrors()){
            product.setId(id);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/products/update-product";
        }
        try {
            Product existingProduct = productService.getProductById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

            if (!imageFile.isEmpty()) {
                String imageFileName = saveFile(imageFile);
                existingProduct.setImage(imageFileName);
            }

            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setQuantity(product.getQuantity());

            productService.updateProduct(existingProduct);



        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            if (!imageFile.isEmpty()) {
//                product.setImage(imageFile.getBytes());
//            }
//            productService.updateProduct(product);
//            redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("message", "Failed to update product!");
//        }
//        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return "redirect:/products";
    }
    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get("images", fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return fileName;
    }

}
