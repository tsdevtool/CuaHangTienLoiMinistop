package com.example.Melistop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String addressShip;
    private String numberPhone;
    private String email;
    private String description;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
