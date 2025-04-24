package edu.miu.cs489.adswebapp.dto.request;

import jakarta.validation.constraints.*;


public record DentistRequestDTO(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,
        @NotBlank(message = "Password is required")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must be minimum 8 characters, at least one letter and one number"
        )
        String password,
        @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
        String phoneNumber,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Specialization is required")
        String specialization
) {
}

