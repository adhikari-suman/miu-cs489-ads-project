package edu.miu.cs489.adswebapp.respository;

import edu.miu.cs489.adswebapp.model.Surgery;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurgeryRepository extends JpaRepository<Surgery, Integer> {
    Optional<Surgery> findBySurgeryNo(String surgeryNo);

    Optional<Surgery> findTopByOrderByIdDesc();
}
