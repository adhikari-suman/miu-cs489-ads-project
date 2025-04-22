package edu.miu.cs489.adswebapp.security.service.impl;

import edu.miu.cs489.adswebapp.exception.patient.DuplicatePatientFoundException;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.respository.PatientRepository;
import edu.miu.cs489.adswebapp.respository.UserRepository;
import edu.miu.cs489.adswebapp.security.dto.request.AuthenticationRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.request.PatientRegistrationRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.response.AuthenticationSuccessResponseDTO;
import edu.miu.cs489.adswebapp.security.mapper.AuthenticationMapper;
import edu.miu.cs489.adswebapp.security.model.Role;
import edu.miu.cs489.adswebapp.security.model.User;
import edu.miu.cs489.adswebapp.security.service.AuthenticationService;
import edu.miu.cs489.adswebapp.security.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PatientRepository     patientRepository;
    private final UserRepository        userRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationMapper  mapper;
    private final JwtService            jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationSuccessResponseDTO registerPatient(PatientRegistrationRequestDTO patientRegistrationRequestDTO) {
        Patient patient = mapper.patientRegistrationRequestDTOToPatient(patientRegistrationRequestDTO);

        if (userRepository.existsUserByUsername(patient.getUsername())) {
            throw new DuplicatePatientFoundException(patient.getUsername() + " already exists");
        }

        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patient.setRole(Role.PATIENT);

        Patient savedPatient = patientRepository.save(patient);

        String accessToken = jwtService.generateToken(savedPatient);

        return new AuthenticationSuccessResponseDTO(accessToken);
    }

    @Override
    public AuthenticationSuccessResponseDTO loginUser(AuthenticationRequestDTO authenticationRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.username(),
                        authenticationRequestDTO.password()
                ));

        User user = (User) authentication.getPrincipal();

        System.out.println("Current logged in user: "+ user);

        String accessToken = jwtService.generateToken(user);

        return new AuthenticationSuccessResponseDTO(accessToken);
    }
}
