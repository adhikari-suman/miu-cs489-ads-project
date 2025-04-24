package edu.miu.cs489.adswebapp.mapper;

import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.BillResponseDTO;
import edu.miu.cs489.adswebapp.model.Appointment;
import edu.miu.cs489.adswebapp.model.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = AddressMapper.class)
public interface AppointmentMapper {
    AppointmentResponseDTO appointmentToAppointmentResponseDTO(Appointment appointment);
}
