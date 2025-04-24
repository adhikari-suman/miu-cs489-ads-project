package edu.miu.cs489.adswebapp.service.impl;


import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.exception.appointment.AppointmentNotFoundException;
import edu.miu.cs489.adswebapp.exception.appointment.InvalidAppointmentStateException;
import edu.miu.cs489.adswebapp.exception.dentist.DentistNotFoundException;
import edu.miu.cs489.adswebapp.exception.surgery.SurgeryNotFoundException;
import edu.miu.cs489.adswebapp.mapper.AppointmentMapper;
import edu.miu.cs489.adswebapp.model.Appointment;
import edu.miu.cs489.adswebapp.model.AppointmentStatus;
import edu.miu.cs489.adswebapp.model.Dentist;
import edu.miu.cs489.adswebapp.model.Surgery;
import edu.miu.cs489.adswebapp.respository.AppointmentRepository;
import edu.miu.cs489.adswebapp.respository.DentistRepository;
import edu.miu.cs489.adswebapp.respository.SurgeryRepository;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import edu.miu.cs489.adswebapp.util.WeekRangeUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper     appointmentMapper;
    private final DentistRepository     dentistRepository;
    private final SurgeryRepository     surgeryRepository;


    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Page<AppointmentResponseDTO> getAppointments(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        return appointmentRepository.findAll(pageRequest).map(appointmentMapper::appointmentToAppointmentResponseDTO);
    }

    @Override
    public AppointmentResponseDTO getAppointmentByAppointmentId(String appointmentId) {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId)
                                                       .orElseThrow(
                                                               () -> new AppointmentNotFoundException(String.format(
                                                                       "No appointment found with appointment id: %s",
                                                                       appointmentId
                                                                                                                   )));


        return appointmentMapper.appointmentToAppointmentResponseDTO(appointment);
    }

    @Override
    public void scheduleAppointmentForDentist(String appointmentId, String dentistId) {
        // Find the appointment by appointment id
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId)
                                                       .orElseThrow(
                                                               () -> new AppointmentNotFoundException(String.format(
                                                                       "No appointment found with appointment id: %s",
                                                                       appointmentId
                                                                                                                   )));

        // Check if the appointment is in a pending state
        if (appointment.getAppointmentStatus() != AppointmentStatus.PENDING) {
            throw new InvalidAppointmentStateException(
                    String.format("Appointment with appointment id: %s is not in pending state.", appointmentId));
        }

        // Get the dentist by dentist id
        Dentist dentist = dentistRepository.findByDentistId(dentistId)
                                           .orElseThrow(() -> new DentistNotFoundException(
                                                   String.format("No dentist found with dentist id: %s", dentistId)));


        int
                totalScheduledAppointments
                = appointmentRepository.countByDentistIdAndAppointmentStatusInAndAppointmentDateTimeBetween(
                dentist.getId(), new AppointmentStatus[]{AppointmentStatus.SCHEDULED, AppointmentStatus.COMPLETED},
                WeekRangeUtil.getStartOfWeek(appointment.getAppointmentDateTime()),
                WeekRangeUtil.getEndOfWeek(appointment.getAppointmentDateTime())
                                                                                                           );

        if (totalScheduledAppointments >= 5) {
            throw new InvalidAppointmentStateException(
                    String.format(
                            "Dentist with dentist id: %s has already scheduled 5 appointments for the week.",
                            dentistId
                                 ));
        }

        appointment.setDentist(dentist);
        appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        appointmentRepository.save(appointment);
    }

    @Override
    public Page<AppointmentResponseDTO> getAppointmentsForSurgeryBySurgeryNo(
            String surgeryNo,
            int page,
            int size,
            String sortBy,
            String sortDirection
                                                                            ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        Surgery surgery = surgeryRepository.findBySurgeryNo(surgeryNo)
                                           .orElseThrow(() -> new SurgeryNotFoundException(
                                 String.format("No surgery found with surgery no: %s", surgeryNo)));

        return appointmentRepository.findAllBySurgeryId(surgery.getId(), pageRequest)
                .map(appointmentMapper::appointmentToAppointmentResponseDTO);
    }
}
