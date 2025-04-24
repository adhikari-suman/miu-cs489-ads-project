package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.SurgeryResponseDTO;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import edu.miu.cs489.adswebapp.service.SurgeryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/surgeries")
@RequiredArgsConstructor
public class SurgeryController {
    private final SurgeryService     surgeryService;
    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<Page<SurgeryResponseDTO>> getSurgeries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "surgeryNo") String sortBy,
            @RequestParam(defaultValue = "asc") String order
                                                                ) {
        return ResponseEntity.ok(surgeryService.getAllSurgeries(page, size, sortBy, order));
    }

    @GetMapping("/{surgeryNo}")
    public ResponseEntity<SurgeryResponseDTO> getSurgeryBySurgeryNo(
            @PathVariable("surgeryNo") String surgeryNo
                                                                   ) {
        return ResponseEntity.ok(surgeryService.getSurgeryBySurgeryNo(surgeryNo));
    }

    @GetMapping("/{surgeryNo}/appointments")
    public ResponseEntity<Page<AppointmentResponseDTO>> getAppointmentsForSurgeryBySurgeryNo(
            @PathVariable("surgeryNo") String surgeryNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentId") String sortBy,
            @RequestParam(defaultValue = "asc") String order
                                                                                            ) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsForSurgeryBySurgeryNo(surgeryNo, page, size, sortBy, order));
    }
}
