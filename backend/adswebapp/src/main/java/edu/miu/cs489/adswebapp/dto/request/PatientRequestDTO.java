package edu.miu.cs489.adswebapp.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Date;


public record PatientRequestDTO(
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must be minimum 8 characters, at least one letter and one number"
        )
        String password,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+1\\d{10}$", message = "Phone number must be in format +1XXXXXXXXXX")
        String phoneNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Address is required")
        @Valid
        AddressRequestDTO address,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        Date dateOfBirth
//        List<AppointmentRequestDTO> appointments
) {
}