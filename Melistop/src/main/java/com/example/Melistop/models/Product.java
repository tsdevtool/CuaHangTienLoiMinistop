package com.example.Melistop.models;

import javax.annotation.processing.Generated;
import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String description;
    private String image;
    private int quantity;



    @ManyToOne
    @JoinColumn(name = "category id")
    private Category category;

    public void reduceQuantity(int quantity){
        if(this.quantity >= quantity)
            this.quantity -= quantity;
        else
            throw  new IllegalArgumentException("Not enough stock available");
    }
}


