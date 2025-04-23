package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.UserResponseDTO;
import edu.miu.cs489.adswebapp.security.dto.request.CredentialUpdateRequestDTO;
import edu.miu.cs489.adswebapp.security.service.AuthenticationService;
import edu.miu.cs489.adswebapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final PatientService patientService;
    private final AuthenticationService authenticationService;

    @PreAuthorize("#username == authentication.name")
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(
            @PathVariable("username") String username
                                                            ) {
        UserResponseDTO userResponseDTO = authenticationService.getUserByUsername(username);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PreAuthorize("#username == authentication.name")
    @PatchMapping("/{username}/credentials")
    public ResponseEntity<Void> updateCredentialsForUsername(
            @PathVariable("username") String username,
            @Validated @RequestBody CredentialUpdateRequestDTO credentialUpdateRequestDTO
                                                              ) {
        authenticationService.updatePassword(username, credentialUpdateRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#username == authentication.name")
    @GetMapping("/{username}/patient-detail")
    public ResponseEntity<PatientResponseDTO> getPatientDetailByUsername(
            @PathVariable("username") String username
                                                                        ){
        PatientResponseDTO patientResponseDTO = patientService.getPatientByUsername(username);

        return ResponseEntity.ok(patientResponseDTO);
    }
}
