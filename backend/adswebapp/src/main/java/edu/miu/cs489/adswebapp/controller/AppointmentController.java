package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.respository.AppointmentRepository;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;


    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDTO>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDateTime") String sortBy,
            @RequestParam(defaultValue = "asc") String order
                                                                          ) {
        Page<AppointmentResponseDTO> appointmentResponseDTOs = appointmentService.getAppointments(
                page, size, sortBy, order);
        return ResponseEntity.ok(appointmentResponseDTOs);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentByAppointmentId(
            @PathVariable("appointmentId") String appointmentId
                                                                               ) {
        return ResponseEntity.ok(appointmentService.getAppointmentByAppointmentId(appointmentId));
    }

    @PatchMapping("/{appointmentId}/dentists/{dentistId}")
    public ResponseEntity<Object> assignADoctorToAppointmentWithAppointmentId(
            @PathVariable("appointmentId") String appointmentId,
            @PathVariable("dentistId") String dentistId,
            @Validated
            @NotNull(message = "billAmount cannot be null")
            @Min(value = 1, message = "billAmount must be greater than 0")
            @RequestParam(value = "billAmount")BigDecimal billAmount
            ) {

        appointmentService.scheduleAppointmentForDentist(appointmentId, dentistId, billAmount);

        return ResponseEntity.noContent().build();
    }

}
