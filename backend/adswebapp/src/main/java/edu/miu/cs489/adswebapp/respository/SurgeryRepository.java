package edu.miu.cs489.adswebapp.respository;

import edu.miu.cs489.adswebapp.model.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurgeryRepository extends JpaRepository<Surgery, Integer> {
}
