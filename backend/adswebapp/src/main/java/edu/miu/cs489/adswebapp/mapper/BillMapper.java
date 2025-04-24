package edu.miu.cs489.adswebapp.mapper;

import edu.miu.cs489.adswebapp.dto.response.BillResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.DentistResponseDTO;
import edu.miu.cs489.adswebapp.model.Bill;
import edu.miu.cs489.adswebapp.model.Dentist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = AddressMapper.class)
public interface BillMapper {
    BillResponseDTO billToBillResponseDTO(Bill bill);
}
