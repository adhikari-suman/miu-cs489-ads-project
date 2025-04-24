package edu.miu.cs489.adswebapp.mapper;

import edu.miu.cs489.adswebapp.dto.request.PatientRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.DentistResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.model.Dentist;
import edu.miu.cs489.adswebapp.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = AddressMapper.class)
public interface DentistMapper {
    DentistResponseDTO dentistToDentistResponseDTO(Dentist dentist);
}
