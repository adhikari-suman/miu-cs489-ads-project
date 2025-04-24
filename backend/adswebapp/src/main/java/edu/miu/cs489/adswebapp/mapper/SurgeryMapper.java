package edu.miu.cs489.adswebapp.mapper;

import edu.miu.cs489.adswebapp.dto.response.SurgeryResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.UserResponseDTO;
import edu.miu.cs489.adswebapp.model.Surgery;
import edu.miu.cs489.adswebapp.security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = AddressMapper.class)
public interface SurgeryMapper {

    @Mapping(source = "address", target = "address")
    SurgeryResponseDTO surgeryToSurgeryResponseDTO(Surgery surgery);
}
