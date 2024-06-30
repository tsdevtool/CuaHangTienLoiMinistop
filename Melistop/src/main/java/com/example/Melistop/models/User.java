package com.example.Melistop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Ten cua nguoi dung
    @Column(name = "name", length = 50)
    @NotBlank(message = "Vui lòng nhập tên người dùng")
    @Size(min = 1, max = 50, message = "Tên người dùng không quá 50 kí tự")
    private String name;

    //Mat khau
    @Column(name = "password", length = 250)
    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu")
    private String confirmPassword;

    //Email - khong bat buoc
    @Column(name = "email", length = 50, unique = true)
    @Size(max = 50, message = "Email không quá 50 kí tự")
    @Email
    private String email;

    //Dia chi mac dinh - khong bat buoc
    @Column(name = "address", length = 50)
    @Size(max = 250, message = "Địa chỉ nhà không quá 50 kí tự")
    private String address;

    //Ten tai khoan
    @Column(name = "username", length = 10, unique = true)
    @Length(min = 10, max = 10, message = "Số điện thoại phải là 10 số")
    @Pattern(regexp = "^[0-9]*$", message = "Số điện thoại phải là kiểu số")
    private String username;

    @Column(name = "provider", length = 50)
    private String provider;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "is_locked")
    private boolean locked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> userRoles = this.getRoles();
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}