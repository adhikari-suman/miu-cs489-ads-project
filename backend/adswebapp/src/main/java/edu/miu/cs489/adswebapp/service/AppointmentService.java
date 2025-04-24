package edu.miu.cs489.adswebapp.service;

import edu.miu.cs489.adswebapp.dto.request.PatientRequestAppointmentRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.model.Appointment;
import edu.miu.cs489.adswebapp.model.AppointmentStatus;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface AppointmentService {
    Appointment addAppointment(Appointment appointment);

    Page<AppointmentResponseDTO> getAppointments(int page, int size, String sortBy, String sortDirection);

    Page<AppointmentResponseDTO> getAppointmentsForSurgeryBySurgeryNo(
            String surgeryNo,
            int page,
            int size,
            String sortBy,
            String sortDirection
                                                                     );

    AppointmentResponseDTO getAppointmentByAppointmentId(String appointmentId);

    void scheduleAppointmentForDentist(String appointmentId, String dentistId, BigDecimal billAmount);

    Page<AppointmentResponseDTO> getAppointmentsForPatientByPatientNo(
            String patientNo,
            int page,
            int size,
            String sortBy,
            String sortDirection
                                                                     );

    AppointmentResponseDTO requestAppointmentForPatient(
            String patientNo,
            PatientRequestAppointmentRequestDTO patientRequestAppointmentRequestDTO
                                                       );

    void payBillForPatient(String appointmentId, String patientNo);

    Page<AppointmentResponseDTO> getAppointmentsForDentistByDentistId(
            String dentistId,
            int page,
            int size,
            String sortBy,
            String order
                                                                     );

    AppointmentResponseDTO getAppointmentByAppointmentIdAndDentistId(String appointmentId, String dentistId);

    AppointmentResponseDTO updateAppointStatusForDentistByDentistIdAndAppointmentId(
            String dentistId,
            String appointmentId,
            AppointmentStatus appointmentStatus
                                                                   );
}
