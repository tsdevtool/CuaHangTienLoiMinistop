package com.example.Melistop.DTO;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
