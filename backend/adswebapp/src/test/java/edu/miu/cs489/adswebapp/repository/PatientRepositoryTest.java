package edu.miu.cs489.adswebapp.repository;

import edu.miu.cs489.adswebapp.model.Address;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.security.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.Month;
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
        patient.setDateOfBirth(LocalDate.of(1987, Month.APRIL, 14)); // Apr 14, 1987
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
    @DisplayName("Patient should not be found by username when not exists")
    void givenUsername_whenFindByUsername_thenReturnEmptyOptional() {
        // given
        patientRepository.deleteAll();
        entityManager.flush();

        // when
        Optional<Patient> optionalPatient = patientRepository.findByUsername(patient.getUsername());

        // then
        assertTrue(optionalPatient.isEmpty(), "Patient should not be found");
    }

    @Test
    @DisplayName("Patient should be found by patientNo (case-insensitive)")
    void givenPatientNo_whenFindByPatientNo_thenReturnPatient() {
        // given
        patientRepository.save(patient);
        entityManager.flush();

        // when
        Optional<Patient> optionalPatient = patientRepository.findByPatientNoEqualsIgnoreCase(patient.getPatientNo());
        Patient foundPatient = optionalPatient.get();

        // then
        assertNotNull(foundPatient, "Patient not found");
        assertEquals(patient.getPatientNo(), foundPatient.getPatientNo(), "Patient patientNo does not match");
    }

    @Test
    @DisplayName("Patient should not be found by patientNo when not exists")
    void givenPatientNo_whenFindByPatientNo_thenReturnEmptyOptional() {
        // given
        patientRepository.deleteAll();
        entityManager.flush();

        // when
        Optional<Patient> optionalPatient = patientRepository.findByPatientNoEqualsIgnoreCase(patient.getPatientNo());

        // then
        assertTrue(optionalPatient.isEmpty(), "Patient should not be found");
    }

    @Test
    @DisplayName("Patient should be found by search string")
    void givenSearchString_whenFindAllBySearchString_thenReturnPatients() {
        // given
        patientRepository.save(patient);
        entityManager.flush();

        // when
        String        search       = "john";
        Page<Patient> patientsPage = patientRepository.findAllBySearchString(search, Pageable.unpaged());

        // then
        assertNotNull(patientsPage, "Patients page should not be null");
        assertFalse(patientsPage.isEmpty(), "Patients should be found with search string");
        assertTrue(patientsPage.getContent().stream().anyMatch(p -> p.getFirstName().equals("John")),
                   "Patient with first name 'John' should be found");
    }

    @Test
    @DisplayName("No Patient should be found by search string when no match")
    void givenSearchString_whenFindAllBySearchString_thenReturnEmptyPage() {
        // given
        patientRepository.save(patient);
        entityManager.flush();

        // when
        String search = "notexisting";
        Page<Patient> patientsPage = patientRepository.findAllBySearchString(search, Pageable.unpaged());

        // then
        assertNotNull(patientsPage, "Patients page should not be null");
        assertTrue(patientsPage.isEmpty(), "No patients should be found with search string");
    }

    @Test
    @DisplayName("Patient should be found by top ID")
    void givenPatient_whenFindTopByOrderByIdDesc_thenReturnPatient() {
        // given
        patientRepository.save(patient);
        entityManager.flush();

        // when
        Optional<Patient> optionalPatient = patientRepository.findTopByOrderByIdDesc();

        // then
        assertNotNull(optionalPatient, "Patient should be found");
        assertTrue(optionalPatient.isPresent(), "Patient should be present");
        assertEquals(patient.getUsername(), optionalPatient.get().getUsername(), "Patient username does not match");
    }
}
