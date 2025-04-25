package edu.miu.cs489.adswebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.security.config.NoSecurityConfiguration;
import edu.miu.cs489.adswebapp.security.configuration.SecurityConfiguration;
import edu.miu.cs489.adswebapp.security.filter.JwtFilter;
import edu.miu.cs489.adswebapp.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AddressController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SecurityConfiguration.class, JwtFilter.class
                })
        }
)
@Import(NoSecurityConfiguration.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressResponseDTO addressDTO;

    @BeforeEach
    void setUp() {
        // Create an address DTO with id and location
        addressDTO = new AddressResponseDTO(1, "123 Health St, City");
    }

    @Test
    @DisplayName("Given a request to get all addresses, when successful, then return status OK and list of addresses")
    void testGetAllAddresses() throws Exception {
        // Given
        Page<AddressResponseDTO> page = new PageImpl<>(List.of(addressDTO));
        Mockito.when(addressService.findAll(any(PageRequest.class)))
               .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/addresses"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].id").value(1))
               .andExpect(jsonPath("$.content[0].location").value("123 Health St, City"));
    }

    @Test
    @DisplayName("Given a request to get all addresses with pagination, when successful, then return status OK and paged list of addresses")
    void testGetAddressesWithPagination() throws Exception {
        // Given
        Page<AddressResponseDTO> page = new PageImpl<>(List.of(addressDTO));
        Mockito.when(addressService.findAll(any(PageRequest.class)))
               .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/addresses")
                                .param("page", "1")
                                .param("size", "5")
                                .param("sortBy", "id")
                                .param("order", "desc"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].id").value(1))
               .andExpect(jsonPath("$.content[0].location").value("123 Health St, City"));
    }
}
