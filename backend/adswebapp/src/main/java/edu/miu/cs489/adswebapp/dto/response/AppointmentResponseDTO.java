package edu.miu.cs489.adswebapp.dto.response;

import edu.miu.cs489.adswebapp.model.*;

import java.time.LocalDate;

public record AppointmentResponseDTO(
    Integer id,
    PatientResponseDTO patient,
    DentistResponseDTO dentist,
    SurgeryResponseDTO surgery,
    LocalDate appointmentDateTime,
    AppointmentStatus appointmentStatus,
    BillResponseDTO bill){
}