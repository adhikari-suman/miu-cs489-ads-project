package edu.miu.cs489.adswebapp.dto.response;

import edu.miu.cs489.adswebapp.model.*;

import java.time.LocalDate;

public record AppointmentResponseDTO(String appointmentId, PatientResponseDTO patient, DentistResponseDTO dentist,
                                     SurgeryResponseDTO surgery, LocalDate appointmentDateTime,
                                     AppointmentStatus appointmentStatus, BillResponseDTO bill) {
}