package edu.miu.cs489.adswebapp.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CredentialUpdateRequestDTO(
        @NotBlank(message = "Password is required")
        @NotNull(message = "Password cannot be null")
        String password,
        @NotBlank(message = "New password is required")
        @NotNull(message = "New password cannot be null")
        String newPassword
) {
}
