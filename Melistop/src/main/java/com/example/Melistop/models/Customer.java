package com.example.Melistop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
@ToString
@Builder
@Table(name = "customers")
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number_phone", length = 10, unique = true)
    @NotBlank(message = "Số điện thoại là dữ liệu bắt buộc")
    @Length(min = 10, max = 10, message = "Số điện thoại là 10 chữ số")
    @Pattern(regexp = "^[0-9]*$", message = "Số điện thoại phải là chữ số")
    private String numberPhone;

    @Column(name = "password", length = 250)
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Column(name = "last_name")
    @NotBlank(message = "Họ không được để trống")
    private String lastName;

    @Column(name = "first_name")
    @NotBlank(message = "Tên không được để trống")
    private String firstName;

    @Column(name = "email", length = 50, unique = true)
    @NotBlank(message = "Email không được để trống")
    @Size(min = 1, max = 50, message = "Email gồm từ 1 đến 50 kí tự")
    @Email
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_role", joinColumns = @JoinColumn(name="customer_id"), inverseJoinColumns = @JoinColumn(name = "role id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Set<RoleCustomer> customersRoles = this.getRoles();
        return customersRoles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return numberPhone;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;

    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)   return true;
        if (o == null || Hibernate.getClass(this)!=Hibernate.getClass(o))
            return false;
        Customer user = (Customer) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }
}
