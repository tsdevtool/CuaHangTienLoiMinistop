package com.example.Melistop.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numberPhone")
    private String numberPhone;

    @Column(name = "password")
    private String password;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "isLocked")
    private Boolean isLocked;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;


}
