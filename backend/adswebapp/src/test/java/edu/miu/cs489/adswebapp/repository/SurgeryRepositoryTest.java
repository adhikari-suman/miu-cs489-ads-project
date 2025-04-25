package edu.miu.cs489.adswebapp.repository;

import edu.miu.cs489.adswebapp.model.Address;
import edu.miu.cs489.adswebapp.model.Surgery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class SurgeryRepositoryTest {

    @Autowired
    private SurgeryRepository surgeryRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Surgery           surgery1;
    private Surgery surgery2;

    @BeforeEach
    public void setUp() {
        Address address1 = new Address(null, "123 Main St, Springfield");
        Address address2 = new Address(null, "123 Main St, Springfield");
        surgery1 = new Surgery(null, "SURG-101", address1, "555-555-5555", null);
        surgery2 = new Surgery(null, "SURG-102", address2, "555-555-5556", null);
    }

    @Test
    @DisplayName("Given SurgeryNo exists, when searching by SurgeryNo, then return the corresponding Surgery")
    public void givenSurgeryNoExists_WhenSearchingBySurgeryNo_ThenReturnCorrespondingSurgery() {
        // given
        surgeryRepository.save(surgery1);
        entityManager.flush();

        // Test the findBySurgeryNo method
        Optional<Surgery> surgery = surgeryRepository.findBySurgeryNo("SURG-101");

        assertTrue(surgery.isPresent());
        assertEquals("SURG-101", surgery.get().getSurgeryNo());
    }

    @Test
    @DisplayName("Given SurgeryNo does not exist, when searching by SurgeryNo, then return empty")
    public void givenSurgeryNoDoesNotExist_WhenSearchingBySurgeryNo_ThenReturnEmpty() {
        // given
        surgeryRepository.deleteAll();
        entityManager.flush();

        // Test with a non-existing surgery number
        Optional<Surgery> surgery = surgeryRepository.findBySurgeryNo("SURG-999");

        assertFalse(surgery.isPresent());
    }

    @Test
    @DisplayName("Given multiple Surgeries, when searching for the most recent Surgery, then return the most recent Surgery by ID")
    public void givenMultipleSurgeries_WhenSearchingForMostRecentSurgery_ThenReturnMostRecentSurgery() {
        // given
        surgeryRepository.save(surgery1);
        surgeryRepository.save(surgery2);
        entityManager.flush();

        // Test the findTopByOrderByIdDesc method
        Optional<Surgery> surgery = surgeryRepository.findTopByOrderByIdDesc();

        assertTrue(surgery.isPresent());
        assertEquals(surgery2.getId(), surgery.get().getId());  // Surgery2 is the most recent because it was saved later
    }

    @Test
    @DisplayName("Given no Surgeries exist, when searching for the most recent Surgery, then return empty")
    public void givenNoSurgeriesExist_WhenSearchingForMostRecentSurgery_ThenReturnEmpty() {
        surgeryRepository.deleteAll(); // Deleting all surgeries
        entityManager.flush();

        // Test when the repository is empty
        Optional<Surgery> surgery = surgeryRepository.findTopByOrderByIdDesc();

        assertFalse(surgery.isPresent());
    }
}
