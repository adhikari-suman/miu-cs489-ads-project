package edu.miu.cs489.adswebapp.security.service.impl;

import edu.miu.cs489.adswebapp.dto.response.UserResponseDTO;
import edu.miu.cs489.adswebapp.mapper.PatientMapper;
import edu.miu.cs489.adswebapp.mapper.UserMapper;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.repository.PatientRepository;
import edu.miu.cs489.adswebapp.repository.UserRepository;
import edu.miu.cs489.adswebapp.security.dto.request.AuthenticationRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.request.CredentialUpdateRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.request.PatientRegistrationRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.response.AuthenticationSuccessResponseDTO;
import edu.miu.cs489.adswebapp.security.exception.user.DuplicateUserFieldException;
import edu.miu.cs489.adswebapp.security.exception.user.InvalidCredentialsException;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private final UserMapper userMapper;
    private final PatientMapper patientMapper;

    @Override
    public AuthenticationSuccessResponseDTO registerPatient(PatientRegistrationRequestDTO patientRegistrationRequestDTO) {
        Patient patient = patientMapper.patientRegistrationRequestDTOToPatient(patientRegistrationRequestDTO);
        userRepository.findByEmail(patient.getEmail()).ifPresent((a) -> {
            throw new DuplicateUserFieldException(String.format("Email %s already exists", patient.getEmail()));
        });

        userRepository.findByUsername(patient.getUsername()).ifPresent((a) -> {
            throw new DuplicateUserFieldException(String.format("Username %s already exists", patient.getUsername()));
        });

        String nextPatientNo = null;

        Optional<Patient> optionalPatient = patientRepository.findTopByOrderByIdDesc();

        if(optionalPatient.isPresent()){
            Patient topPatient = optionalPatient.get();

            String currentPatientId =
                    topPatient.getPatientNo().split(Patient.PATIENT_ID_PREFIX)[1];

            nextPatientNo =
                    Patient.PATIENT_ID_PREFIX + (Integer.parseInt(currentPatientId) + 1);
        } else {
            nextPatientNo =
                    Patient.PATIENT_ID_PREFIX + 1;
        }


        patient.setPassword(passwordEncoder.encode(patientRegistrationRequestDTO.password()));
        patient.setPatientNo(nextPatientNo);
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

    @Override
    public void updatePassword(String username, CredentialUpdateRequestDTO credentialUpdateRequestDTO) {
       User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

       boolean isCurrentPasswordValid = passwordEncoder.matches(credentialUpdateRequestDTO.password(),
                                                              user.getPassword());

       if(!isCurrentPasswordValid){
           throw new InvalidCredentialsException("Current password is invalid");
       }

       user.setPassword(passwordEncoder.encode(credentialUpdateRequestDTO.newPassword()));

       userRepository.save(user);
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return userMapper.userToUserResponseDTO(user);
    }
}
