package edu.miu.cs489.adswebapp.respository;


import edu.miu.cs489.adswebapp.security.model.Role;
import edu.miu.cs489.adswebapp.security.model.User;
import edu.miu.cs489.adswebapp.model.Dentist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Dentist tony;

    @BeforeEach
    void setUp() {
        tony = new Dentist();
        tony.setFirstName("Tony");
        tony.setLastName("Smith");
        tony.setUsername("tsmith");
        tony.setDentistId("DENT-1");
        tony.setPassword("pwd");
        tony.setPhoneNumber("123-456");
        tony.setEmail("tony@dental.com");
        tony.setSpecialization("General Dentistry");
        tony.setRole(Role.DENTIST);
    }

    @Test
    @DisplayName("User (Dentist) should be found by username")
    void givenUsername_whenFindByUsername_thenReturnUser() {
        // given
        userRepository.save(tony);
        entityManager.flush();

        // when
        Optional<User> optionalUser = userRepository.findByUsername(tony.getUsername());
        User foundUser = optionalUser.get();

        // then
        assertNotNull(foundUser, "User not found");
        assertEquals(tony.getUsername(), foundUser.getUsername(), "User username does not match");
        assertTrue(foundUser instanceof Dentist, "User should be a Dentist");
    }

    @Test
    @DisplayName("User (Dentist) should not be found when username does not exist")
    void givenUsername_whenFindByUsername_thenReturnEmptyOptional() {
        // given
        userRepository.deleteAll();
        entityManager.flush();

        // when
        Optional<User> optionalUser = userRepository.findByUsername(tony.getUsername());

        // then
        assertTrue(optionalUser.isEmpty(), "User should not be found");
    }

    @Test
    @DisplayName("User (Dentist) should be found by email")
    void givenEmail_whenFindByEmail_thenReturnUser() {
        // given
        userRepository.save(tony);
        entityManager.flush();

        // when
        Optional<User> optionalUser = userRepository.findByEmail(tony.getEmail());
        User foundUser = optionalUser.get();

        // then
        assertNotNull(foundUser, "User not found");
        assertEquals(tony.getEmail(), foundUser.getEmail(), "User email does not match");
        assertTrue(foundUser instanceof Dentist, "User should be a Dentist");
    }

    @Test
    @DisplayName("User (Dentist) should not be found by email if does not exist")
    void givenEmail_whenFindByEmail_thenReturnEmptyOptional() {
        // given
        userRepository.deleteAll();
        entityManager.flush();

        // when
        Optional<User> optionalUser = userRepository.findByEmail(tony.getEmail());

        // then
        assertTrue(optionalUser.isEmpty(), "User should not be found");
    }

    @Test
    @DisplayName("Should return true if username exists")
    void givenUsername_whenExistsByUsername_thenReturnTrue() {
        // given
        userRepository.save(tony);
        entityManager.flush();

        // when
        boolean exists = userRepository.existsUserByUsername(tony.getUsername());

        // then
        assertTrue(exists, "Username should exist");
    }

    @Test
    @DisplayName("Should return false if username does not exist")
    void givenUsername_whenExistsByUsername_thenReturnFalse() {
        // given
        userRepository.deleteAll();
        entityManager.flush();

        // when
        boolean exists = userRepository.existsUserByUsername(tony.getUsername());

        // then
        assertFalse(exists, "Username should not exist");
    }
}
