package edu.miu.cs489.adswebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs489.adswebapp.dto.response.*;
import edu.miu.cs489.adswebapp.model.AppointmentStatus;
import edu.miu.cs489.adswebapp.security.config.NoSecurityConfiguration;
import edu.miu.cs489.adswebapp.security.configuration.SecurityConfiguration;
import edu.miu.cs489.adswebapp.security.filter.JwtFilter;
import edu.miu.cs489.adswebapp.service.AppointmentService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AppointmentController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SecurityConfiguration.class, JwtFilter.class
                })
        }
)
@Import(NoSecurityConfiguration.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v1/appointments should return paged appointments")
    void getAllAppointments_shouldReturnPagedAppointments() throws Exception {
        // Given
        AppointmentResponseDTO response = new AppointmentResponseDTO(
                "APT-001", null, null, null,
                LocalDateTime.now(), AppointmentStatus.SCHEDULED, null
        );
        Page<AppointmentResponseDTO> page = new PageImpl<>(List.of(response));
        Mockito.when(appointmentService.getAppointments(anyInt(), anyInt(), anyString(), anyString()))
               .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/appointments")
                                .param("page", "0")
                                .param("size", "10")
                                .param("sortBy", "appointmentDateTime")
                                .param("order", "asc"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].appointmentId").value("APT-001"));
    }

    @Test
    @DisplayName("GET /api/v1/appointments/{id} should return appointment")
    void getAppointmentById_shouldReturnAppointment() throws Exception {
        // Given
        String appointmentId = "APT-001";
        AppointmentResponseDTO response = new AppointmentResponseDTO(
                appointmentId, null, null, null,
                LocalDateTime.now(), AppointmentStatus.SCHEDULED, null
        );
        Mockito.when(appointmentService.getAppointmentByAppointmentId(appointmentId))
               .thenReturn(response);

        // When & Then
        String uri = String.format("/api/v1/appointments/%s", appointmentId);
        mockMvc.perform(get(uri))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.appointmentId").value(appointmentId));
    }

    @Test
    @DisplayName("PATCH /api/v1/appointments/{id}/dentists/{id} should assign dentist and set bill")
    void assignDentistToAppointment_shouldSucceed() throws Exception {
        // Given
        String appointmentId = "APT-001";
        String dentistId = "DNT-001";
        BigDecimal billAmount = new BigDecimal("120.00");

        // When & Then
        String uri = String.format("/api/v1/appointments/%s/dentists/%s", appointmentId, dentistId);
        mockMvc.perform(patch(uri).param("billAmount", billAmount.toPlainString()))
               .andExpect(status().isNoContent());

        // Then
        Mockito.verify(appointmentService).scheduleAppointmentForDentist(appointmentId, dentistId, billAmount);
    }

    @Test
    @DisplayName("PATCH with invalid bill amount should return 400")
    void assignDentistToAppointment_shouldFailWithInvalidBill() throws Exception {
        // Given
        String appointmentId = "APT-001";
        String dentistId = "DNT-001";

        // When & Then
        String uri = String.format("/api/v1/appointments/%s/dentists/%s", appointmentId, dentistId);
        mockMvc.perform(patch(uri).param("billAmount", "0"))
               .andExpect(status().isBadRequest());
    }
}
