package com.example.Melistop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "order details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "product id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @PrePersist
    public void AdjustProductQuantity(){
        if(product!=null)
            product.reduceQuantity(quantity);
    }
}
