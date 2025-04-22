package edu.miu.cs489.adswebapp.service;

import edu.miu.cs489.adswebapp.model.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment addAppointment(Appointment appointment);
    List<Appointment> getAllAppointments();

}
