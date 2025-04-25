package edu.miu.cs489.adswebapp.repository;

import edu.miu.cs489.adswebapp.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsUserByUsername(String username);

    Optional<User> findByEmail(String email);
}
