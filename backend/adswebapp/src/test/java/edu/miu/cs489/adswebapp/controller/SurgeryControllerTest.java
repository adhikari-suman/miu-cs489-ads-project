package edu.miu.cs489.adswebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs489.adswebapp.dto.response.*;
import edu.miu.cs489.adswebapp.model.AppointmentStatus;
import edu.miu.cs489.adswebapp.model.BillStatus;
import edu.miu.cs489.adswebapp.security.config.NoSecurityConfiguration;
import edu.miu.cs489.adswebapp.security.configuration.SecurityConfiguration;
import edu.miu.cs489.adswebapp.security.filter.JwtFilter;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import edu.miu.cs489.adswebapp.service.SurgeryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = SurgeryController.class, excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = {
                SecurityConfiguration.class, JwtFilter.class
        }
        )
}
)
@Import(NoSecurityConfiguration.class)
public class SurgeryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SurgeryService surgeryService;

    @MockitoBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private SurgeryResponseDTO surgeryDTO;
    private AppointmentResponseDTO appointmentDTO;

    @BeforeEach
    void setUp() {
        surgeryDTO = new SurgeryResponseDTO(1, "S001", new AddressResponseDTO(1, "Health St"), "1234567890");

        PatientResponseDTO patientDTO = new PatientResponseDTO("P001", "John", "Doe", new AddressResponseDTO(1, "City"));
        DentistResponseDTO dentistDTO = new DentistResponseDTO("D001", "Dr.", "Smith", "Orthodontics");
        BillResponseDTO    billDTO    = new BillResponseDTO(1, BigDecimal.valueOf(150.00), BillStatus.PENDING);
        appointmentDTO = new AppointmentResponseDTO("A001", patientDTO, dentistDTO, surgeryDTO, LocalDateTime.now(), AppointmentStatus.PENDING, billDTO);
    }

    @Test
    @DisplayName("Given a request to get all surgeries, when successful, then return status OK and list of surgeries")
    void testGetAllSurgeries() throws Exception {
        // Given
        Page<SurgeryResponseDTO> page = new PageImpl<>(List.of(surgeryDTO));
        Mockito.when(surgeryService.getAllSurgeries(anyInt(), anyInt(), anyString(), anyString()))
               .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/surgeries"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].surgeryNo").value("S001"));
    }

    @Test
    @DisplayName("Given a request for a specific surgery, when successful, then return status OK and surgery details")
    void testGetSurgeryBySurgeryNo() throws Exception {
        // Given
        Mockito.when(surgeryService.getSurgeryBySurgeryNo("S001")).thenReturn(surgeryDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/surgeries/S001"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.surgeryNo").value("S001"));
    }

    @Test
    @DisplayName("Given a request for appointments for a specific surgery, when successful, then return status OK and list of appointments")
    void testGetAppointmentsForSurgeryBySurgeryNo() throws Exception {
        // Given
        Page<AppointmentResponseDTO> page = new PageImpl<>(List.of(appointmentDTO));
        Mockito.when(appointmentService.getAppointmentsForSurgeryBySurgeryNo(eq("S001"), anyInt(), anyInt(), anyString(), anyString()))
               .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/surgeries/S001/appointments"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].appointmentId").value("A001"));
    }
}
