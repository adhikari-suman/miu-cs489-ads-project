package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.exception.patient.PatientNotFoundException;
import edu.miu.cs489.adswebapp.mapper.PatientMapper;
import edu.miu.cs489.adswebapp.model.Address;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.repository.PatientRepository;
import edu.miu.cs489.adswebapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;
    private PatientResponseDTO patientResponseDTO;

    @BeforeEach
    void setUp() {
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO(1, "123 Main St");
        patientResponseDTO = new PatientResponseDTO("PAT-001", "John", "Doe", addressResponseDTO);

        patient = new Patient();
        patient.setPatientNo("PAT-001");
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setAddress(new Address(1, "123 Main St"));
    }

    @Test
    @DisplayName("Given valid page request, when getAllPatients is called, then it should return paginated list of patients")
    void givenValidPageRequest_whenGetAllPatients_thenReturnsPaginatedList() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "patientNo"));
        Page<Patient> page = new PageImpl<>(Collections.singletonList(patient));

        when(patientRepository.findAll(pageRequest)).thenReturn(page);
        when(patientMapper.patientToPatientResponseDTO(patient)).thenReturn(patientResponseDTO);

        // When
        Page<PatientResponseDTO> result = patientService.getAllPatients(0, 10, "patientNo", "ASC");

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals("PAT-001", result.getContent().get(0).patientNo());
        verify(patientRepository).findAll(pageRequest);
    }

    @Test
    @DisplayName("Given valid patientNo, when getPatientByPatientNo is called, then it should return patient response")
    void givenValidPatientNo_whenGetPatientByPatientNo_thenReturnsPatientResponse() {
        // Given
        when(patientRepository.findByPatientNoEqualsIgnoreCase("PAT-001")).thenReturn(Optional.of(patient));
        when(patientMapper.patientToPatientResponseDTO(patient)).thenReturn(patientResponseDTO);

        // When
        PatientResponseDTO result = patientService.getPatientByPatientNo("PAT-001");

        // Then
        assertNotNull(result);
        assertEquals("PAT-001", result.patientNo());
        verify(patientRepository).findByPatientNoEqualsIgnoreCase("PAT-001");
    }

    @Test
    @DisplayName("Given invalid patientNo, when getPatientByPatientNo is called, then it should throw PatientNotFoundException")
    void givenInvalidPatientNo_whenGetPatientByPatientNo_thenThrowsPatientNotFoundException() {
        // Given
        when(patientRepository.findByPatientNoEqualsIgnoreCase("PAT-999")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                PatientNotFoundException.class,
                () -> patientService.getPatientByPatientNo("PAT-999"));
        verify(patientRepository).findByPatientNoEqualsIgnoreCase("PAT-999");
    }

    @Test
    @DisplayName("Given valid username, when getPatientByUsername is called, then it should return patient response")
    void givenValidUsername_whenGetPatientByUsername_thenReturnsPatientResponse() {
        // Given
        when(patientRepository.findByUsername("johndoe")).thenReturn(Optional.of(patient));
        when(patientMapper.patientToPatientResponseDTO(patient)).thenReturn(patientResponseDTO);

        // When
        PatientResponseDTO result = patientService.getPatientByUsername("johndoe");

        // Then
        assertNotNull(result);
        assertEquals("PAT-001", result.patientNo());
        verify(patientRepository).findByUsername("johndoe");
    }

    @Test
    @DisplayName("Given invalid username, when getPatientByUsername is called, then it should throw PatientNotFoundException")
    void givenInvalidUsername_whenGetPatientByUsername_thenThrowsPatientNotFoundException() {
        // Given
        when(patientRepository.findByUsername("invaliduser")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                PatientNotFoundException.class,
                () -> patientService.getPatientByUsername("invaliduser"));
        verify(patientRepository).findByUsername("invaliduser");
    }
}
