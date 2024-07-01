package com.example.Melistop.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
    @Email(message = "Email should be valid")
    private String email;

    @Length(min = 10, max = 10, message = "Phone must be 10 characters")
    @Pattern(regexp = "^[0-9]*$", message = "Phone must be number")
    @NotBlank(message = "Phone is required")
    private String username;

    private String address;

    private String provider;

    private boolean isLocked;

    private Set<Long> roles; // Add this field to store role IDs

    private String password;
    private String confirmPassword;
}
