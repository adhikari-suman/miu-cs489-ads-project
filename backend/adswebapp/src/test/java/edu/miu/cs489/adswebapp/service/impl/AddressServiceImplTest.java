package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.mapper.AddressMapper;
import edu.miu.cs489.adswebapp.model.Address;
import edu.miu.cs489.adswebapp.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressResponseDTO addressResponseDTO;

    @BeforeEach
    void setUp() {
        addressResponseDTO = new AddressResponseDTO(1, "123 Main St, Springfield");

        address = new Address();
        address.setId(1);
        address.setLocation("123 Main St, Springfield");
    }

    @Test
    @DisplayName("Given valid page request, when findAll is called, then returns paginated addresses")
    void givenValidPageRequest_whenFindAll_thenReturnsPaginatedAddresses() {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<Address> page = new PageImpl<>(List.of(address));

        when(addressRepository.findAll(pageable)).thenReturn(page);
        when(addressMapper.addressToAddressResponseDTO(address)).thenReturn(addressResponseDTO);

        // When
        Page<AddressResponseDTO> result = addressService.findAll(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("123 Main St, Springfield", result.getContent().get(0).location());
        verify(addressRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Given empty page, when findAll is called, then returns empty page")
    void givenEmptyPage_whenFindAll_thenReturnsEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<Address> page = new PageImpl<>(List.of());

        when(addressRepository.findAll(pageable)).thenReturn(page);

        // When
        Page<AddressResponseDTO> result = addressService.findAll(pageable);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        verify(addressRepository).findAll(pageable);
    }
}
