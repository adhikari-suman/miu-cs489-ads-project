package edu.miu.cs489.adswebapp.service.impl;


import edu.miu.cs489.adswebapp.model.Appointment;
import edu.miu.cs489.adswebapp.respository.AppointmentRepository;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class AppointServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;


    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
