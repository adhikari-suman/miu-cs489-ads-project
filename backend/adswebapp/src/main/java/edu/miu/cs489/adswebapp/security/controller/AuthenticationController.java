package edu.miu.cs489.adswebapp.security.controller;

import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.security.dto.request.AuthenticationRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.request.PatientRegistrationRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.response.AuthenticationSuccessResponseDTO;
import edu.miu.cs489.adswebapp.security.model.User;
import edu.miu.cs489.adswebapp.security.service.AuthenticationService;
import edu.miu.cs489.adswebapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationSuccessResponseDTO> registerPatient(
            @Validated @RequestBody PatientRegistrationRequestDTO patientRegistrationRequestDTO
                                                                           ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(authenticationService.registerPatient(patientRegistrationRequestDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationSuccessResponseDTO> loginUser(
            @Validated @RequestBody AuthenticationRequestDTO authenticationRequestDTO
                                                                           ) {
        return ResponseEntity.ok(authenticationService.loginUser(authenticationRequestDTO));
    }
}
