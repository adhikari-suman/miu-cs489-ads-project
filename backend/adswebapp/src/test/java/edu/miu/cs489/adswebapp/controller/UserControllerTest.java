package edu.miu.cs489.adswebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.DentistResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.UserResponseDTO;
import edu.miu.cs489.adswebapp.security.config.NoSecurityConfiguration;
import edu.miu.cs489.adswebapp.security.configuration.SecurityConfiguration;
import edu.miu.cs489.adswebapp.security.dto.request.CredentialUpdateRequestDTO;
import edu.miu.cs489.adswebapp.security.filter.JwtFilter;
import edu.miu.cs489.adswebapp.security.service.AuthenticationService;
import edu.miu.cs489.adswebapp.service.DentistService;
import edu.miu.cs489.adswebapp.service.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(
        controllers = UserController.class, excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = {
                SecurityConfiguration.class, JwtFilter.class
        }
        )
}
)
@Import(NoSecurityConfiguration.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @MockitoBean
    private DentistService dentistService;

    @MockitoBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v1/users/{username} - should return user by username")
    void getUserByUsername_shouldReturnUser() throws Exception {
        // Given
        String username = "tony";
        UserResponseDTO user = new UserResponseDTO(username, "Tony", "Stark");
        Mockito.when(authenticationService.getUserByUsername(username)).thenReturn(user);

        // When & Then
        mockMvc.perform(get(String.format("/api/v1/users/%s", username)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("PATCH /api/v1/users/{username}/credentials - should update credentials")
    void updateCredentialsForUsername_shouldSucceed() throws Exception {
        // Given
        String username = "tony";
        CredentialUpdateRequestDTO requestDTO = new CredentialUpdateRequestDTO("Ironman123", "Ironman321");

        // When & Then
        mockMvc.perform(patch(String.format("/api/v1/users/%s/credentials", username))
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(requestDTO)))
               .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/v1/users/{username}/patient-detail - should return patient detail")
    void getPatientDetailByUsername_shouldReturnPatient() throws Exception {
        // Given
        String username = "steve";
        AddressResponseDTO address = new AddressResponseDTO(1, "Baker St");
        PatientResponseDTO patient = new PatientResponseDTO("PNT-001", "Steve", "Rogers", address);
        Mockito.when(patientService.getPatientByUsername(username)).thenReturn(patient);

        // When & Then
        mockMvc.perform(get(String.format("/api/v1/users/%s/patient-detail", username)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.patientNo").value("PNT-001"));
    }

    @Test
    @DisplayName("GET /api/v1/users/{username}/dentist-detail - should return dentist detail")
    void getDentistDetailByUsername_shouldReturnDentist() throws Exception {
        // Given
        String username = "bruce";
        DentistResponseDTO dentist = new DentistResponseDTO("DNT-002", "Bruce", "Banner", "Oral Surgeon");
        Mockito.when(dentistService.getDentistByUsername(username)).thenReturn(dentist);

        // When & Then
        mockMvc.perform(get(String.format("/api/v1/users/%s/dentist-detail", username)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.dentistId").value("DNT-002"));
    }
}
