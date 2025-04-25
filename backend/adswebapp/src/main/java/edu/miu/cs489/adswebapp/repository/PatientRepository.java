package edu.miu.cs489.adswebapp.repository;

import edu.miu.cs489.adswebapp.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByUsername(String username);
    Optional<Patient> findByPatientNoEqualsIgnoreCase(String patientNo);
    @Query("""
    SELECT p FROM Patient p
    JOIN p.address a
    WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(p.username) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(p.email) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(p.patientNo) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(p.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%'))
""")
    Page<Patient> findAllBySearchString(@Param("search") String search, Pageable pageable);

    Optional<Patient> findTopByOrderByIdDesc();
}
