package edu.miu.cs489.adswebapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    @GetMapping
    public ResponseEntity<List<Object>> getAllAppointments() {
        return ResponseEntity.ok(List.of());
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
