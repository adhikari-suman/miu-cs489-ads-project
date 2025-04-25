package edu.miu.cs489.adswebapp.repository;

import edu.miu.cs489.adswebapp.model.OfficeManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfficeManagerRepository extends JpaRepository<OfficeManager, Integer> {
    Optional<OfficeManager> findByUsername(String username);
}
