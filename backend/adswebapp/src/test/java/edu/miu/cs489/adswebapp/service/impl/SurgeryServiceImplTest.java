package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.SurgeryResponseDTO;
import edu.miu.cs489.adswebapp.exception.surgery.SurgeryNotFoundException;
import edu.miu.cs489.adswebapp.mapper.SurgeryMapper;
import edu.miu.cs489.adswebapp.model.Address;
import edu.miu.cs489.adswebapp.model.Surgery;
import edu.miu.cs489.adswebapp.repository.SurgeryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurgeryServiceImplTest {

    @Mock
    private SurgeryRepository surgeryRepository;

    @Mock
    private SurgeryMapper surgeryMapper;

    @InjectMocks
    private SurgeryServiceImpl surgeryService;

    private Surgery surgery;
    private SurgeryResponseDTO surgeryResponseDTO;

    @BeforeEach
    void setUp() {
        Address address = new Address();
        address.setId(1);
        address.setLocation("123 Main St");

        surgery = new Surgery();
        surgery.setId(1);
        surgery.setSurgeryNo("SURG-001");
        surgery.setPhoneNumber("555-1234");
        surgery.setAddress(address);

        surgeryResponseDTO = new SurgeryResponseDTO(
                1,
                "SURG-001",
                new AddressResponseDTO(1, "123 Main St"),
                "555-1234"
        );
    }

    @Test
    @DisplayName("Given valid page request, when getAllSurgeries is called, then it should return paginated list of surgeries")
    void givenValidPageRequest_whenGetAllSurgeries_thenReturnsPaginatedList() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "surgeryNo"));
        Page<Surgery> page = new PageImpl<>(Collections.singletonList(surgery));

        when(surgeryRepository.findAll(pageRequest)).thenReturn(page);
        when(surgeryMapper.surgeryToSurgeryResponseDTO(surgery)).thenReturn(surgeryResponseDTO);

        // When
        Page<SurgeryResponseDTO> result = surgeryService.getAllSurgeries(0, 10, "surgeryNo", "ASC");

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals("SURG-001", result.getContent().get(0).surgeryNo());
        verify(surgeryRepository).findAll(pageRequest);
    }

    @Test
    @DisplayName("Given valid surgeryNo, when getSurgeryBySurgeryNo is called, then it should return surgery response")
    void givenExistingSurgeryNo_whenGetSurgeryBySurgeryNo_thenReturnsSurgeryResponse() {
        // Given
        when(surgeryRepository.findBySurgeryNo("SURG-001")).thenReturn(Optional.of(surgery));
        when(surgeryMapper.surgeryToSurgeryResponseDTO(surgery)).thenReturn(surgeryResponseDTO);

        // When
        SurgeryResponseDTO result = surgeryService.getSurgeryBySurgeryNo("SURG-001");

        // Then
        assertNotNull(result);
        assertEquals("SURG-001", result.surgeryNo());
        verify(surgeryRepository).findBySurgeryNo("SURG-001");
    }

    @Test
    @DisplayName("Given invalid surgeryNo, when getSurgeryBySurgeryNo is called, then it should throw SurgeryNotFoundException")
    void givenNonexistentSurgeryNo_whenGetSurgeryBySurgeryNo_thenThrowsSurgeryNotFoundException() {
        // Given
        when(surgeryRepository.findBySurgeryNo("SURG-999")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                SurgeryNotFoundException.class,
                () -> surgeryService.getSurgeryBySurgeryNo("SURG-999"));
        verify(surgeryRepository).findBySurgeryNo("SURG-999");
    }
}
