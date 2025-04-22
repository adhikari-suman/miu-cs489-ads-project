package edu.miu.cs489.adswebapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/surgeries")
@RequiredArgsConstructor
public class SurgeryController {
    @GetMapping
    public ResponseEntity<List<Object>> getSurgeries(

                                                    ) {
        return ResponseEntity.ok(
                List.of("Surgery 1", "Surgery 2", "Surgery 3", "Surgery 4", "Surgery 5")
                                );
    }

    @GetMapping("/{surgeryNo}")
    public ResponseEntity<Object> getSurgeryBySurgeryNo(
            @PathVariable("surgeryNo") String surgeryNo
                                                   ) {
        return ResponseEntity.ok(
                "Surgery with surgery no: " + surgeryNo + " found."
                                );
    }

    @GetMapping("/{surgeryNo}/appointments")
    public ResponseEntity<List<Object>> getAppointmentsForSurgeryBySurgeryNo(
            @PathVariable("surgeryNo") String surgeryNo
                                                       ) {
        return ResponseEntity.ok(
                List.of("Appointment 1", "Appointment 2", "Appointment 3", "Appointment 4", "Appointment 5")
                                );
    }


}
