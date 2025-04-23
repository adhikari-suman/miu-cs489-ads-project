package edu.miu.cs489.adswebapp.respository;

import edu.miu.cs489.adswebapp.model.Address;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.security.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PatientRepositoryTest {
    @Autowired private PatientRepository patientRepository;

    @Autowired private TestEntityManager entityManager;



    private Patient patient;

    @BeforeEach
    void setUp() {
        Address address = new Address(null, "123 Main St, Springfield");

        patient = new Patient();
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
    }

    @Test
    @DisplayName("Patient should be found by username")
    void givenUsername_whenFindByUsername_thenReturnPatient() {
        // given
        patientRepository.save(patient);
        entityManager.flush();

        // when
        Optional<Patient> optionalPatient = patientRepository.findByUsername(patient.getUsername());
        Patient foundPatient = optionalPatient.get();

        // then
        assertNotNull(foundPatient, "Patient not found");
        assertEquals(patient.getUsername(), foundPatient.getUsername(), "Patient username does not match");
    }

    @Test
    @DisplayName("Patient should be found by username")
    void givenUsername_whenFindByUsername_thenReturnEmptyOptional() {
        // given
        patientRepository.deleteAll();
        entityManager.flush();

        // when
        Optional<Patient> optionalPatient = patientRepository.findByUsername(patient.getUsername());

        // then
        assertTrue(optionalPatient.isEmpty(), "Patient should not be found");
    }
}