package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Page<AppointmentResponseDTO> appointmentResponseDTOs = appointmentService.getAppointments(page, size, sortBy,
                                                                                                order);
        return ResponseEntity.ok(appointmentResponseDTOs);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Object> getAppointmentByAppointmentId(@PathVariable("appointmentId") String appointmentId) {
        return ResponseEntity.ok("Appointment with appointment id: " + appointmentId + " found.");
    }

    @PatchMapping("/{appointmentId}/dentists/{dentistId}")
    public ResponseEntity<Object> assignADoctorToAppointmentWithAppointmentId(
            @PathVariable("appointmentId") String appointmentId,
            @PathVariable("dentistId") String dentistId
                                                                             ) {
        return ResponseEntity.ok(
                "Appointment with appointment id: " + appointmentId + " assigned to Dentist with dentist id: " + dentistId + " successfully."
                                );
    }

}
