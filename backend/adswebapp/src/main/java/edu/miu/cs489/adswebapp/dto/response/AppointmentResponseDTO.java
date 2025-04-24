package edu.miu.cs489.adswebapp.dto.response;

import edu.miu.cs489.adswebapp.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AppointmentResponseDTO(String appointmentId, PatientResponseDTO patient, DentistResponseDTO dentist,
                                     SurgeryResponseDTO surgery, LocalDateTime appointmentDateTime,
                                     AppointmentStatus appointmentStatus, BillResponseDTO bill) {
}