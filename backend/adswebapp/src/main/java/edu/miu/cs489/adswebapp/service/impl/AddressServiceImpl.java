package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.mapper.AddressMapper;
import edu.miu.cs489.adswebapp.respository.AddressRepository;
import edu.miu.cs489.adswebapp.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper     addressMapper;

    @Override
    public Page<AddressResponseDTO> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable).map(
                addressMapper::addressToAddressResponseDTO
                                                      );
    }
}
