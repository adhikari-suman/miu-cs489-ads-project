package edu.miu.cs489.adswebapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRequestDTO(
        @NotNull(message = "Location cannot be null")
        @NotBlank(message = "Location cannot be blank")
        String location) {
}
