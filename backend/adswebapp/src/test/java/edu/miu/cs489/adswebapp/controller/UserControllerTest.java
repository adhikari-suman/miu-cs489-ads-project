package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.request.PatientRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.exception.patient.PatientNotFoundException;
import edu.miu.cs489.adswebapp.mapper.AddressMapper;
import edu.miu.cs489.adswebapp.mapper.PatientMapper;
import edu.miu.cs489.adswebapp.model.Address;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.security.config.NoSecurityConfiguration;
import edu.miu.cs489.adswebapp.security.configuration.SecurityConfiguration;
import edu.miu.cs489.adswebapp.security.filter.JwtFilter;
import edu.miu.cs489.adswebapp.security.model.Role;
import edu.miu.cs489.adswebapp.security.service.JwtService;
import edu.miu.cs489.adswebapp.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired private MockMvc mockMvc;

    @MockitoBean private PatientService patientService;

    @MockitoBean private PatientMapper patientMapper;

    private PatientRequestDTO  patientRequestDTO;
    private PatientResponseDTO patientResponseDTO;
    private Patient            patient;

    @BeforeEach
    void setUp() {
        Address address = new Address(901, "123 Main St, Springfield");

        patient = new Patient();
        patient.setId(900);
        patient.setPatientNo("P110");
        patient.setFirstName("John");
        patient.setLastName("Walker");
        patient.setUsername("jwalker");
        patient.setPassword("pwd");
        patient.setPhoneNumber("321-459");
        patient.setEmail("john@patients.com");
        patient.setDateOfBirth(new Date(87, 3, 14)); // Apr 14, 1987
        patient.setAddress(address);
        patient.setRole(Role.PATIENT);

        patientResponseDTO = patientMapper.patientToPatientResponseDTO(patient);
    }

    @Test
    @DisplayName("GET /users/:username/patient-detail should return a patient")
    void givenUsername_whenGet_thenReturnPatientResponseDTO() throws Exception {
        // given
        Mockito.when(patientService.getPatientByUsername(patient.getUsername())).thenReturn(patientResponseDTO);
        Mockito.when(patientMapper.patientToPatientResponseDTO(patient)).thenReturn(patientResponseDTO);

        // when
        mockMvc.perform(
                       MockMvcRequestBuilders.get(String.format("/api/v1/users/%s/patient-detail", patient.getUsername()))
                                             .contentType(MediaType.APPLICATION_JSON))
               // then
               .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("GET /users/:username/patient-detail should return 404")
    void givenUsername_whenGet_thenReturnNoContentFound() throws Exception {
        // given
        Mockito.when(patientService.getPatientByUsername(patient.getUsername()))
               .thenThrow(new PatientNotFoundException(
                       String.format("Patient with username %s not found", patient.getUsername())));

        // when
        mockMvc.perform(
                       MockMvcRequestBuilders.get(String.format("/api/v1/users/%s/patient-detail", patient.getUsername()))
                                             .contentType(MediaType.APPLICATION_JSON))
               // then
               .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
    }
}