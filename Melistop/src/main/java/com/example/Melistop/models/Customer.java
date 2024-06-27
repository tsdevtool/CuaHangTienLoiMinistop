package com.example.Melistop.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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

    @Column(name = "number_phone")
    private String numberPhone;

    @Column(name = "password")
    private String password;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "registration_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
}
