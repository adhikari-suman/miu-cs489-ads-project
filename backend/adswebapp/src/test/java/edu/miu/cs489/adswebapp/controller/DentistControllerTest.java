package edu.miu.cs489.adswebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs489.adswebapp.dto.request.DentistRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.DentistResponseDTO;
import edu.miu.cs489.adswebapp.model.AppointmentStatus;
import edu.miu.cs489.adswebapp.security.config.NoSecurityConfiguration;
import edu.miu.cs489.adswebapp.security.configuration.SecurityConfiguration;
import edu.miu.cs489.adswebapp.security.filter.JwtFilter;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import edu.miu.cs489.adswebapp.service.DentistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = DentistController.class, excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = {
                SecurityConfiguration.class, JwtFilter.class
        }
        )
}
)
@Import(NoSecurityConfiguration.class)
class DentistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DentistService dentistService;

    @MockitoBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v1/dentists - should return paged dentists")
    void getAllDentists_shouldReturnPagedDentists() throws Exception {
        // Given
        DentistResponseDTO response = new DentistResponseDTO("DNT-001", "Tony", "Stark", "Orthodontist");
        Page<DentistResponseDTO> page = new PageImpl<>(List.of(response));
        Mockito.when(dentistService.getAllDentists(anyInt(), anyInt(), anyString(), anyString()))
               .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/dentists")
                                .param("page", "0")
                                .param("size", "10")
                                .param("sortBy", "dentistId")
                                .param("order", "asc"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].dentistId").value("DNT-001"));
    }

    @Test
    @DisplayName("POST /api/v1/dentists - should create a dentist")
    void addADentist_shouldReturnCreatedDentist() throws Exception {
        // Given
        DentistRequestDTO request = new DentistRequestDTO(
                "Tony", "Stark", "ironman", "Ironman123", "1234567890", "tony@stark.com", "Orthodontist"
        );
        DentistResponseDTO response = new DentistResponseDTO("DNT-001", "Tony", "Stark", "Orthodontist");
        Mockito.when(dentistService.addADentist(any())).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/dentists")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.dentistId").value("DNT-001"));
    }

    @Test
    @DisplayName("GET /api/v1/dentists/{id} - should return dentist by ID")
    void getADentistById_shouldReturnDentist() throws Exception {
        // Given
        String dentistId = "DNT-001";
        DentistResponseDTO response = new DentistResponseDTO(dentistId, "Tony", "Stark", "Orthodontist");
        Mockito.when(dentistService.getDentistByDentistId(dentistId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get(String.format("/api/v1/dentists/%s", dentistId)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.dentistId").value(dentistId));
    }

    @Test
    @DisplayName("GET /api/v1/dentists/{id}/appointments - should return appointments for dentist")
    void getAppointmentsForDentist_shouldReturnAppointments() throws Exception {
        // Given
        String dentistId = "DNT-001";
        AppointmentResponseDTO appointment = new AppointmentResponseDTO(
                "APT-001", null, null, null, LocalDateTime.now(), AppointmentStatus.SCHEDULED, null
        );
        Page<AppointmentResponseDTO> page = new PageImpl<>(List.of(appointment));
        Mockito.when(appointmentService.getAppointmentsForDentistByDentistId(anyString(), anyInt(), anyInt(), anyString(), anyString()))
               .thenReturn(page);

        // When & Then
        mockMvc.perform(get(String.format("/api/v1/dentists/%s/appointments", dentistId)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].appointmentId").value("APT-001"));
    }

    @Test
    @DisplayName("GET /api/v1/dentists/{id}/appointments/{appointmentId} - should return specific appointment")
    void getAppointmentForDentistById_shouldReturnAppointment() throws Exception {
        // Given
        String dentistId = "DNT-001";
        String appointmentId = "APT-001";
        AppointmentResponseDTO response = new AppointmentResponseDTO(
                appointmentId, null, null, null, LocalDateTime.now(), AppointmentStatus.SCHEDULED, null
        );
        Mockito.when(appointmentService.getAppointmentByAppointmentIdAndDentistId(appointmentId, dentistId))
               .thenReturn(response);

        // When & Then
        mockMvc.perform(get(String.format("/api/v1/dentists/%s/appointments/%s", dentistId, appointmentId)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.appointmentId").value(appointmentId));
    }

    @Test
    @DisplayName("PATCH /api/v1/dentists/{id}/appointments/{appointmentId} - should update appointment status")
    void updateAppointmentStatus_shouldSucceed() throws Exception {
        // Given
        String dentistId = "DNT-001";
        String appointmentId = "APT-001";
        AppointmentResponseDTO response = new AppointmentResponseDTO(
                appointmentId, null, null, null, LocalDateTime.now(), AppointmentStatus.COMPLETED, null
        );
        Mockito.when(appointmentService.updateAppointStatusForDentistByDentistIdAndAppointmentId(
                dentistId, appointmentId, AppointmentStatus.COMPLETED)).thenReturn(response);

        // When & Then
        mockMvc.perform(patch(String.format("/api/v1/dentists/%s/appointments/%s", dentistId, appointmentId))
                                .param("appointmentStatus", "COMPLETED"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.appointmentStatus").value("COMPLETED"));
    }
}
