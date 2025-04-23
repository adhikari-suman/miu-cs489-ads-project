package edu.miu.cs489.adswebapp.security.service;

import edu.miu.cs489.adswebapp.dto.response.UserResponseDTO;
import edu.miu.cs489.adswebapp.security.dto.request.AuthenticationRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.request.CredentialUpdateRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.request.PatientRegistrationRequestDTO;
import edu.miu.cs489.adswebapp.security.dto.response.AuthenticationSuccessResponseDTO;
import edu.miu.cs489.adswebapp.security.model.User;

public interface AuthenticationService {
    AuthenticationSuccessResponseDTO registerPatient(PatientRegistrationRequestDTO patientRegistrationRequestDTO);
    AuthenticationSuccessResponseDTO loginUser(AuthenticationRequestDTO authenticationRequestDTO);
    void updatePassword(String username, CredentialUpdateRequestDTO credentialUpdateRequestDTO);
    UserResponseDTO getUserByUsername(String username);
}
