package edu.miu.cs489.adswebapp.mapper;

import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    AddressResponseDTO addressToAddressResponseDTO(Address address);
}
