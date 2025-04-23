package edu.miu.cs489.adswebapp.security.dto.request;

import edu.miu.cs489.adswebapp.dto.request.AddressRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientRegistrationRequestDTO(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
        String phoneNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Patient number is required")
        String patientNo,
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,
        @Valid
        @NotNull
        AddressRequestDTO address
) {
}

