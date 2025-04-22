package edu.miu.cs489.adswebapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dentists")
@RequiredArgsConstructor
public class DentistController {

    @GetMapping
    public ResponseEntity<List<Object>> getAllDentists() {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<Object> addADentist(@RequestBody Object requestBody) {
        return ResponseEntity.status(201).body("Dentist added successfully.");
    }

    @GetMapping("/{dentistId}")
    public ResponseEntity<Object> getADentistByDentistId(@PathVariable("dentistId") String dentistId) {
        return ResponseEntity.ok("Dentist with dentist id: " + dentistId + " found.");
    }

    @GetMapping("/{dentistId}/appointments")
    public ResponseEntity<Object> getAppointmentsForDentistByDentistId(@PathVariable("dentistId") String dentistId) {
        return ResponseEntity.ok("Appointments for Dentists with dentist Id: " + dentistId + " found.");
    }

    @GetMapping("/{dentistId}/appointments/{appointmentId}")
    public ResponseEntity<Object> getAppointmentForDentistByDentistIdAndAppointmentId(
            @PathVariable("dentistId") String dentistId,
            @PathVariable("appointmentId") String appointmentId
                                                                                     ) {
        return ResponseEntity.ok("Appointments for Dentists with dentist Id: " + dentistId + " found.");
    }


    @PatchMapping("/{dentistId}/appointments/{appointmentId}")
    public ResponseEntity<Object> updateAppointmentStatusForAppointmentByAppointmentId(
            @PathVariable("dentistId") String dentistId,
            @PathVariable("appointmentId") String appointmentId
                                                                                      ) {
        return ResponseEntity.ok("Appointment with appointment id: " +
                                 appointmentId +
                                 " for dentist with dentist Id: " +
                                 dentistId +
                                 " completed or cancelled.");
    }

    @PostMapping("/{patientNo}/appointments/{appointmentId}/pay")
    public ResponseEntity<Object> payForAppointmentForPatientByPatientNo(
            @PathVariable("patientNo") String patientNo,
            @PathVariable("appointmentId")
            String appointmentId
                                                                        ) {
        return ResponseEntity.ok(
                String.format(
                        "Payment for appointment Id: %s for patient %s is completed successfully.", appointmentId,
                        patientNo
                             ));
    }
}
