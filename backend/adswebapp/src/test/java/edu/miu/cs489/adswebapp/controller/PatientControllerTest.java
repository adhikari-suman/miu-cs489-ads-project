package edu.miu.cs489.adswebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs489.adswebapp.dto.request.PatientRequestAppointmentRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.*;
import edu.miu.cs489.adswebapp.model.AppointmentStatus;
import edu.miu.cs489.adswebapp.model.BillStatus;
import edu.miu.cs489.adswebapp.security.config.NoSecurityConfiguration;
import edu.miu.cs489.adswebapp.security.configuration.SecurityConfiguration;
import edu.miu.cs489.adswebapp.security.filter.JwtFilter;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import edu.miu.cs489.adswebapp.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = PatientController.class, excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = {
                SecurityConfiguration.class, JwtFilter.class
        }
        )
}
)
@Import(NoSecurityConfiguration.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @MockitoBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientResponseDTO patientDTO;
    private AppointmentResponseDTO appointmentDTO;

    @BeforeEach
    void setUp() {
        patientDTO = new PatientResponseDTO("P001", "John", "Doe", new AddressResponseDTO(1, "City"));

        DentistResponseDTO dentist = new DentistResponseDTO("D001", "Dr.", "Smith", "Orthodontics");
        SurgeryResponseDTO surgery = new SurgeryResponseDTO(1, "Smile Center", new AddressResponseDTO(1, "456 Health " +
                                                                                                       "St"),
                                                            "1234567890");
        BillResponseDTO bill = new BillResponseDTO(1, BigDecimal.valueOf(150.00), BillStatus.PENDING);

        appointmentDTO = new AppointmentResponseDTO("A001", patientDTO, dentist, surgery,
                                                    LocalDateTime.now(), AppointmentStatus.PENDING, bill);
    }

    @Test
    void testGetAllPatients() throws Exception {
        Page<PatientResponseDTO> page = new PageImpl<>(List.of(patientDTO));
        Mockito.when(patientService.getAllPatients(anyInt(), anyInt(), anyString(), anyString()))
               .thenReturn(page);

        mockMvc.perform(get("/api/v1/patients"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].patientNo").value("P001"));
    }

    @Test
    void testGetPatientByPatientNo() throws Exception {
        Mockito.when(patientService.getPatientByPatientNo("P001")).thenReturn(patientDTO);

        mockMvc.perform(get("/api/v1/patients/P001"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testGetAppointmentsOfPatientByPatientNo() throws Exception {
        Page<AppointmentResponseDTO> page = new PageImpl<>(List.of(appointmentDTO));
        Mockito.when(appointmentService.getAppointmentsForPatientByPatientNo(anyString(), anyInt(), anyInt(), anyString(), anyString()))
               .thenReturn(page);

        mockMvc.perform(get("/api/v1/patients/P001/appointments"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].appointmentId").value("A001"));
    }

    @Test
    void testMakeAppointmentForPatientByPatientNo() throws Exception {
        // Ensure it's a valid future date
        LocalDateTime futureTime = LocalDateTime.now().plusHours(1);

        PatientRequestAppointmentRequestDTO requestDTO = new PatientRequestAppointmentRequestDTO(
                "S001", futureTime, 1); // Make sure 1 is a valid addressId

        Mockito.when(appointmentService.requestAppointmentForPatient(eq("P001"), any(PatientRequestAppointmentRequestDTO.class)))
               .thenReturn(appointmentDTO);

        mockMvc.perform(post("/api/v1/patients/P001/appointments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDTO)))
               .andDo(print()) // helpful for debugging
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.appointmentId").value("A001"));
    }

    @Test
    void testPayForAppointmentForPatientByPatientNo() throws Exception {
        mockMvc.perform(post("/api/v1/patients/P001/appointments/A001/pay"))
               .andExpect(status().isNoContent());

        Mockito.verify(appointmentService).payBillForPatient("A001", "P001");
    }
}
