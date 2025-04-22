package edu.miu.cs489.adswebapp.security.mapper;

import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.security.dto.request.PatientRegistrationRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.internal.model.source.MapperConfigOptions;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationMapper {

    @Mapping(source="address", target = "address")
    public Patient patientRegistrationRequestDTOToPatient(PatientRegistrationRequestDTO patientRegistrationRequestDTO);
}
