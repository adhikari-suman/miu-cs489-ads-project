package edu.miu.cs489.adswebapp.respository;

import edu.miu.cs489.adswebapp.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DentistRepository extends JpaRepository<Dentist, Integer> {
    Optional<Dentist> findByUsername(String username);

    Optional<Dentist> findByDentistId(String dentistId);

    Optional<Dentist> findTopByOrderByIdDesc();
}
