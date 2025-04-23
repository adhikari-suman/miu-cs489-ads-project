package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final PatientService patientService;

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserByUsername(
            @PathVariable("username") String username
                                                   ) {
        return ResponseEntity.ok("User with username: " + username + " found.");
    }

    @PatchMapping("/{username}/credentials")
    public ResponseEntity<Object> updateCredentialsForUsername(
            @PathVariable("username") String username
                                                   ) {
        return ResponseEntity.ok("User with username: " + username + " found.");
    }

    @GetMapping("/{username}/patient-detail")
    public ResponseEntity<PatientResponseDTO> getPatientDetailByUsername(
            @PathVariable("username") String username
                                                                        ){
        PatientResponseDTO patientResponseDTO = patientService.getPatientByUsername(username);

        return ResponseEntity.ok(patientResponseDTO);
    }
}
