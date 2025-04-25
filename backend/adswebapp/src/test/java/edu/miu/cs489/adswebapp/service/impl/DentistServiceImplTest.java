package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.request.DentistRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.DentistResponseDTO;
import edu.miu.cs489.adswebapp.exception.dentist.DentistNotFoundException;
import edu.miu.cs489.adswebapp.mapper.DentistMapper;
import edu.miu.cs489.adswebapp.model.Dentist;
import edu.miu.cs489.adswebapp.repository.DentistRepository;
import edu.miu.cs489.adswebapp.repository.UserRepository;
import edu.miu.cs489.adswebapp.security.exception.user.DuplicateUserFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DentistServiceImplTest {

    @Mock
    private DentistRepository dentistRepository;

    @Mock
    private DentistMapper dentistMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DentistServiceImpl dentistService;

    private Dentist dentist;
    private DentistResponseDTO dentistResponseDTO;

    @BeforeEach
    void setUp() {
        dentistResponseDTO = new DentistResponseDTO("DENT-001", "John", "Doe", "Orthodontist");

        dentist = new Dentist();
        dentist.setDentistId("DENT-001");
        dentist.setFirstName("John");
        dentist.setLastName("Doe");
        dentist.setSpecialization("Orthodontist");
    }

    @Test
    @DisplayName("Given valid page request, when getAllDentists is called, then it should return paginated list of dentists")
    void givenValidPageRequest_whenGetAllDentists_thenReturnsPaginatedList() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "dentistId"));
        Page<Dentist> page = new PageImpl<>(Collections.singletonList(dentist));

        when(dentistRepository.findAll(pageRequest)).thenReturn(page);
        when(dentistMapper.dentistToDentistResponseDTO(dentist)).thenReturn(dentistResponseDTO);

        // When
        Page<DentistResponseDTO> result = dentistService.getAllDentists(0, 10, "dentistId", "ASC");

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals("DENT-001", result.getContent().get(0).dentistId());
        verify(dentistRepository).findAll(pageRequest);
    }

    @Test
    @DisplayName("Given valid username, when getDentistByUsername is called, then it should return dentist response")
    void givenValidUsername_whenGetDentistByUsername_thenReturnsDentistResponse() {
        // Given
        when(dentistRepository.findByUsername("johndoe")).thenReturn(Optional.of(dentist));
        when(dentistMapper.dentistToDentistResponseDTO(dentist)).thenReturn(dentistResponseDTO);

        // When
        DentistResponseDTO result = dentistService.getDentistByUsername("johndoe");

        // Then
        assertNotNull(result);
        assertEquals("DENT-001", result.dentistId());
        verify(dentistRepository).findByUsername("johndoe");
    }

    @Test
    @DisplayName("Given invalid username, when getDentistByUsername is called, then it should throw DentistNotFoundException")
    void givenInvalidUsername_whenGetDentistByUsername_thenThrowsDentistNotFoundException() {
        // Given
        when(dentistRepository.findByUsername("invaliduser")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                DentistNotFoundException.class,
                () -> dentistService.getDentistByUsername("invaliduser"));
        verify(dentistRepository).findByUsername("invaliduser");
    }

    @Test
    @DisplayName("Given valid dentistId, when getDentistByDentistId is called, then it should return dentist response")
    void givenValidDentistId_whenGetDentistByDentistId_thenReturnsDentistResponse() {
        // Given
        when(dentistRepository.findByDentistId("DENT-001")).thenReturn(Optional.of(dentist));
        when(dentistMapper.dentistToDentistResponseDTO(dentist)).thenReturn(dentistResponseDTO);

        // When
        DentistResponseDTO result = dentistService.getDentistByDentistId("DENT-001");

        // Then
        assertNotNull(result);
        assertEquals("DENT-001", result.dentistId());
        verify(dentistRepository).findByDentistId("DENT-001");
    }

    @Test
    @DisplayName("Given invalid dentistId, when getDentistByDentistId is called, then it should throw DentistNotFoundException")
    void givenInvalidDentistId_whenGetDentistByDentistId_thenThrowsDentistNotFoundException() {
        // Given
        when(dentistRepository.findByDentistId("DENT-999")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                DentistNotFoundException.class,
                () -> dentistService.getDentistByDentistId("DENT-999"));
        verify(dentistRepository).findByDentistId("DENT-999");
    }

    @Test
    @DisplayName("Given valid dentist request, when addADentist is called, then it should return added dentist response")
    void givenValidDentistRequest_whenAddADentist_thenReturnsAddedDentistResponse() {
        // Given
        DentistRequestDTO dentistRequestDTO = new DentistRequestDTO(
                "John", "Doe", "johndoe", "Password123", "1234567890", "john.doe@example.com", "Orthodontist"
        );
        Dentist dentistToSave = new Dentist();
        dentistToSave.setDentistId("DENT-002");
        dentistToSave.setFirstName("John");
        dentistToSave.setLastName("Doe");
        dentistToSave.setSpecialization("Orthodontist");

        when(dentistMapper.dentistRequestDTOToDentist(dentistRequestDTO)).thenReturn(dentistToSave);
        when(dentistRepository.save(dentistToSave)).thenReturn(dentistToSave);
        when(dentistMapper.dentistToDentistResponseDTO(dentistToSave)).thenReturn(new DentistResponseDTO("DENT-002", "John", "Doe", "Orthodontist"));

        // When
        DentistResponseDTO result = dentistService.addADentist(dentistRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals("DENT-002", result.dentistId());
        verify(dentistRepository).save(dentistToSave);
    }

    @Test
    @DisplayName("Given duplicate email, when addADentist is called, then it should throw DuplicateUserFieldException")
    void givenDuplicateEmail_whenAddADentist_thenThrowsDuplicateUserFieldException() {
        // Given
        DentistRequestDTO dentistRequestDTO = new DentistRequestDTO(
                "John", "Doe", "johndoe", "Password123", "1234567890", "john.doe@example.com", "Orthodontist"
        );
        Dentist dentistToSave = new Dentist();
        dentistToSave.setDentistId("DENT-002");
        dentistToSave.setFirstName("John");
        dentistToSave.setLastName("Doe");
        dentistToSave.setSpecialization("Orthodontist");

        when(dentistMapper.dentistRequestDTOToDentist(dentistRequestDTO)).thenReturn(dentistToSave);
        when(userRepository.findByEmail(dentistToSave.getEmail())).thenReturn(Optional.of(dentistToSave));

        // When / Then
        assertThrows(
                DuplicateUserFieldException.class,
                () -> dentistService.addADentist(dentistRequestDTO));
        verify(userRepository).findByEmail(dentistToSave.getEmail());
    }

    @Test
    @DisplayName("Given duplicate username, when addADentist is called, then it should throw DuplicateUserFieldException")
    void givenDuplicateUsername_whenAddADentist_thenThrowsDuplicateUserFieldException() {
        // Given
        DentistRequestDTO dentistRequestDTO = new DentistRequestDTO(
                "John", "Doe", "johndoe", "Password123", "1234567890", "john.doe@example.com", "Orthodontist"
        );
        Dentist dentistToSave = new Dentist();
        dentistToSave.setDentistId("DENT-002");
        dentistToSave.setFirstName("John");
        dentistToSave.setLastName("Doe");
        dentistToSave.setSpecialization("Orthodontist");

        when(dentistMapper.dentistRequestDTOToDentist(dentistRequestDTO)).thenReturn(dentistToSave);
        when(userRepository.findByUsername(dentistToSave.getUsername())).thenReturn(Optional.of(dentistToSave));

        // When / Then
        assertThrows(
                DuplicateUserFieldException.class,
                () -> dentistService.addADentist(dentistRequestDTO));
        verify(userRepository).findByUsername(dentistToSave.getUsername());
    }
}
