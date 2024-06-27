package com.example.Melistop.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "day_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dayOfBirth;

    @Column(name = "no_identity_card")
    private String noIdentityCard;

    @Column(name = "number_phone")
    private String numberPhone;

    @Column(name = "address")
    private String address;

    @Column(name = "sex")
    private String sex;

    @Column(name = "nationality")
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @OneToMany(mappedBy = "employee")
    private List<Order> orders;
}
