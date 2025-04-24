package edu.miu.cs489.adswebapp.service.impl;


import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.mapper.AppointmentMapper;
import edu.miu.cs489.adswebapp.model.Appointment;
import edu.miu.cs489.adswebapp.respository.AppointmentRepository;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class AppointServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper     appointmentMapper;


    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Page<AppointmentResponseDTO> getAppointments(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        return appointmentRepository.findAll(pageRequest).map(appointmentMapper::appointmentToAppointmentResponseDTO);
    }
}
