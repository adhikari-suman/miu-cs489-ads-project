package edu.miu.cs489.adswebapp.service;

import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    Page<AddressResponseDTO> findAll(Pageable pageable);
}
