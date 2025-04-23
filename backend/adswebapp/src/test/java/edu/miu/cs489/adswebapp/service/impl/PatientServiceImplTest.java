package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.exception.patient.PatientNotFoundException;
import edu.miu.cs489.adswebapp.mapper.AddressMapper;
import edu.miu.cs489.adswebapp.mapper.PatientMapper;
import edu.miu.cs489.adswebapp.model.Address;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.respository.PatientRepository;
import edu.miu.cs489.adswebapp.security.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock private PatientRepository patientRepository;

    @InjectMocks private PatientServiceImpl patientService;

    @Mock private PatientMapper patientMapper;

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

        patientResponseDTO = new PatientResponseDTO(
                patient.getPatientNo(), patient.getFirstName(), patient.getLastName(),
                new AddressResponseDTO(patient.getAddress().getId(), patient.getAddress().getLocation())
        );
    }

    @Test
    @DisplayName("Patient should be found by username")
    void givenUsername_whenFindByUsername_thenReturnPatientResponseDTO() {
        // given
        Mockito.when(patientRepository.findByUsername(patient.getUsername()))
               .thenReturn(java.util.Optional.of(patient));
        Mockito.when(patientMapper.patientToPatientResponseDTO(patient)).thenReturn(patientResponseDTO);
        // when
        PatientResponseDTO foundPatientDTO = patientService.getPatientByUsername(patient.getUsername());

        // then
        assertEquals(patientResponseDTO, foundPatientDTO, "Patient not found");
        assertEquals(patientResponseDTO.firstName(), foundPatientDTO.firstName(), "Patient first name does not match");
        assertEquals(patientResponseDTO.patientNo(), foundPatientDTO.patientNo(), "Patient No. does not match");
    }

    @Test
    @DisplayName("Patient should not be found by username")
    void givenUsername_whenFindByUsername_thenThrowPatientNotFoundException() {
        assertThrows(
                PatientNotFoundException.class, () -> patientService.getPatientByUsername(patient.getUsername()),
                "Patient should not be found by username: " + patient.getUsername() + " "
                    );
    }
}