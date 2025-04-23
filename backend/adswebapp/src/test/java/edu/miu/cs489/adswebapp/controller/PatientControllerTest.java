package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.request.PatientRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.mapper.AddressMapper;
import edu.miu.cs489.adswebapp.mapper.PatientMapper;
import edu.miu.cs489.adswebapp.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Autowired
    private PatientMapper patientMapper;
    private AddressMapper addressMapper;

    private PatientRequestDTO patientRequestDTO;
    private PatientResponseDTO patientResponseDTO;


    @BeforeEach
    void setUp() {
    }
}