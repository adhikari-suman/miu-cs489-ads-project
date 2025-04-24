package edu.miu.cs489.adswebapp.mapper;

import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.security.dto.request.PatientRegistrationRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = AddressMapper.class)
public interface PatientMapper {

    // not needed but still showing
    @Mapping(source = "address", target = "address")
    PatientResponseDTO patientToPatientResponseDTO(Patient patient);

    @Mapping(source="address", target = "address")
    Patient patientRegistrationRequestDTOToPatient(PatientRegistrationRequestDTO patientRegistrationRequestDTO);
}
