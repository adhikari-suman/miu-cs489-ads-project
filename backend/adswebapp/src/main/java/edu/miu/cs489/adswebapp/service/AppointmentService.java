package edu.miu.cs489.adswebapp.service;

import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AppointmentService {
    Appointment addAppointment(Appointment appointment);
    List<Appointment> getAllAppointments();


    Page<AppointmentResponseDTO> getAppointments(
            int page,
            int size,
            String sortBy,
            String sortDirection
                                                );
}
