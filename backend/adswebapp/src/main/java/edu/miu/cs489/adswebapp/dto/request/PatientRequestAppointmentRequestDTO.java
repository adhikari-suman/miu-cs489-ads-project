package edu.miu.cs489.adswebapp.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PatientRequestAppointmentRequestDTO(
        String surgeryNo,
        @NotNull(message = "Appointment date time cannot be null")
        @Future(message = "Appointment date time must be in the future")
        LocalDateTime appointmentDateTime,
        @NotNull(message = "addressId cannot be null")
        @Min(value = 1, message = "addressId cannot be negative or zero")
        Integer addressId
) {
}
