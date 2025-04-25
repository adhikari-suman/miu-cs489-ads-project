package edu.miu.cs489.adswebapp.service.impl;


import edu.miu.cs489.adswebapp.dto.request.PatientRequestAppointmentRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.exception.address.AddressNotFoundException;
import edu.miu.cs489.adswebapp.exception.appointment.AppointmentNotFoundException;
import edu.miu.cs489.adswebapp.exception.appointment.InvalidAppointmentStateException;
import edu.miu.cs489.adswebapp.exception.dentist.DentistNotFoundException;
import edu.miu.cs489.adswebapp.exception.patient.PatientNotFoundException;
import edu.miu.cs489.adswebapp.exception.surgery.SurgeryNotFoundException;
import edu.miu.cs489.adswebapp.mapper.AppointmentMapper;
import edu.miu.cs489.adswebapp.model.*;
import edu.miu.cs489.adswebapp.repository.*;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import edu.miu.cs489.adswebapp.util.WeekRangeUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper     appointmentMapper;
    private final DentistRepository     dentistRepository;
    private final SurgeryRepository     surgeryRepository;
    private final PatientRepository     patientRepository;
    private final AddressRepository     addressRepository;


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
    public void scheduleAppointmentForDentist(String appointmentId, String dentistId, BigDecimal billAmount) {
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

        appointment.getBill().setAmount(billAmount);
        appointment.getBill().setBillStatus(BillStatus.PENDING);

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

    @Override
    public Page<AppointmentResponseDTO> getAppointmentsForPatientByPatientNo(
            String patientNo,
            int page,
            int size,
            String sortBy,
            String sortDirection
                                                                            ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        Patient patient = patientRepository.findByPatientNoEqualsIgnoreCase(patientNo)
                                           .orElseThrow(() -> new PatientNotFoundException(
                                                   String.format("No patient found with patient no: %s", patientNo)));

        return appointmentRepository.findAllByPatientId(patient.getId(), pageRequest)
                                    .map(appointmentMapper::appointmentToAppointmentResponseDTO);
    }

    @Override
    public AppointmentResponseDTO requestAppointmentForPatient(
            String patientNo,
            PatientRequestAppointmentRequestDTO patientRequestAppointmentRequestDTO
                                                              ) {
        Patient patient = patientRepository.findByPatientNoEqualsIgnoreCase(patientNo)
                                           .orElseThrow(() -> new PatientNotFoundException(
                                                   String.format("No patient found with patient no: %s", patientNo)));


        int pendingBillCount = appointmentRepository.countBillsForPatientByBillStatus(
                patient.getPatientNo(),
                BillStatus.PENDING
                                                                                     );

        if (pendingBillCount > 0) {
            throw new InvalidAppointmentStateException(
                    "Patient has pending bills. Cannot request another appointment.");
        }

        Surgery surgery;

        if (patientRequestAppointmentRequestDTO.surgeryNo() != null) {
            surgery = surgeryRepository.findBySurgeryNo(patientRequestAppointmentRequestDTO.surgeryNo())
                                       .orElseThrow(() -> new SurgeryNotFoundException(String.format(
                                               "No surgery found with surgery no: %s",
                                               patientRequestAppointmentRequestDTO.surgeryNo()
                                                                                                    )));
        } else {
            // get the last surgery no
            Optional<Surgery> currentTopSurgery = surgeryRepository.findTopByOrderByIdDesc();

            String newSurgeryNo = null;

            // create a new surgery no
            if (currentTopSurgery.isPresent()) {
                String currentSurgeryNo = currentTopSurgery.get().getSurgeryNo().split(Surgery.SURGERY_ID_PREFIX)[1];

                int currentSurgeryNoInt = Integer.parseInt(currentSurgeryNo);
                currentSurgeryNoInt++;

                newSurgeryNo = Surgery.SURGERY_ID_PREFIX + currentSurgeryNoInt;

            } else {
                newSurgeryNo = Surgery.SURGERY_ID_PREFIX + 1;
            }

            surgery = new Surgery();
            surgery.setSurgeryNo(newSurgeryNo);

            Address address = addressRepository.findById(patientRequestAppointmentRequestDTO.addressId())
                                               .orElseThrow(() -> new AddressNotFoundException(

                                                       String.format(
                                                               "No address found with address id: %s",
                                                               patientRequestAppointmentRequestDTO.addressId()
                                                                    )));

            surgery.setAddress(address);
            surgery.setPhoneNumber(patient.getPhoneNumber());

            surgery = surgeryRepository.save(surgery);
        }


        // Get the most recent appointment number
        Optional<Appointment> foundAppointment = appointmentRepository.findTopByOrderByIdDesc();

        String nextAppointmentId = null;

        if (foundAppointment.isPresent()) {
            String currentAppointmentId = foundAppointment.get().getAppointmentId();

            String[] currentAppointmentIdParts = currentAppointmentId.split(Appointment.APPOINTMENT_ID_PREFIX);

            int currentAppointmentIdInt = Integer.parseInt(currentAppointmentIdParts[1]);
            currentAppointmentIdInt++;

            nextAppointmentId = Appointment.APPOINTMENT_ID_PREFIX + currentAppointmentIdInt;

        } else {
            nextAppointmentId = Appointment.APPOINTMENT_ID_PREFIX + 1;
        }

        Appointment appointment = new Appointment();

        Bill bill = new Bill(null, null, BigDecimal.ZERO, BillStatus.PENDING);

        appointment.setPatient(patient);
        appointment.setAppointmentDateTime(patientRequestAppointmentRequestDTO.appointmentDateTime());
        appointment.setSurgery(surgery);
        appointment.setAppointmentId(nextAppointmentId);
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment.setBill(bill);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.appointmentToAppointmentResponseDTO(savedAppointment);
    }

    @Override
    public void payBillForPatient(String appointmentId, String patientNo) {
        Patient patient = patientRepository.findByPatientNoEqualsIgnoreCase(patientNo)
                                           .orElseThrow(() -> new PatientNotFoundException(
                                                   String.format("No patient found with patient no: %s", patientNo)));

        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId)
                                                       .orElseThrow(
                                                               () -> new AppointmentNotFoundException(String.format(
                                                                       "No appointment found with appointment id: %s",
                                                                       appointmentId
                                                                                                                   )));

        if (appointment.getPatient().getPatientNo() != patient.getPatientNo()) {
            throw new InvalidAppointmentStateException(String.format(
                    "Appointment with appointment id: %s is not for patient with patient no: %s", appointmentId,
                    patientNo
                                                                    ));
        }

        if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
            throw new InvalidAppointmentStateException(String.format(
                    "Appointment with appointment id: %s is in cancelled state. You cannot pay for it.",
                    appointmentId
                                                                    ));
        }

        if (appointment.getBill().getBillStatus() != BillStatus.PENDING) {
            throw new InvalidAppointmentStateException(
                    String.format("Appointment with appointment id: %s has already been paid for.", appointmentId));
        }

        appointment.getBill().setBillStatus(BillStatus.PAID);
        appointmentRepository.save(appointment);
    }

    @Override
    public Page<AppointmentResponseDTO> getAppointmentsForDentistByDentistId(
            String dentistId,
            int page,
            int size,
            String sortBy,
            String order
                                                                            ) {

        Dentist dentist = dentistRepository.findByDentistId(dentistId)
                                           .orElseThrow(() -> new DentistNotFoundException(
                                                   String.format("No dentist found with dentist id: %s", dentistId)));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));

        return appointmentRepository.findByDentistId(dentist.getId(), pageRequest)
                                    .map(appointmentMapper::appointmentToAppointmentResponseDTO);
    }

    @Override
    public AppointmentResponseDTO getAppointmentByAppointmentIdAndDentistId(String appointmentId, String dentistId) {
        Dentist dentist = dentistRepository.findByDentistId(dentistId)
                                           .orElseThrow(() -> new DentistNotFoundException(
                                                   String.format("No dentist found with dentist id: %s", dentistId)));

        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId)
                                                       .orElseThrow(
                                                               () -> new AppointmentNotFoundException(String.format(
                                                                       "No appointment found with appointment id: %s",
                                                                       appointmentId
                                                                                                                   )));

        if (appointment.getDentist() == null || !appointment.getDentist().getId().equals(dentist.getId())) {
            throw new InvalidAppointmentStateException(String.format(
                    "Appointment with appointment id: %s is not for dentist with dentist id: %s", appointmentId,
                    dentistId
                                                                    ));
        }

        return appointmentMapper.appointmentToAppointmentResponseDTO(appointment);
    }

    @Override
    public AppointmentResponseDTO updateAppointStatusForDentistByDentistIdAndAppointmentId(
            String dentistId,
            String appointmentId,
            AppointmentStatus appointmentStatus
                                                                                          ) {
        Dentist dentist = dentistRepository.findByDentistId(dentistId)
                                           .orElseThrow(() -> new DentistNotFoundException(
                                                   String.format("No dentist found with dentist id: %s", dentistId)));

        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId)
                                                       .orElseThrow(
                                                               () -> new AppointmentNotFoundException(String.format(
                                                                       "No appointment found with appointment id: %s",
                                                                       appointmentId
                                                                                                                   )));
        if (appointment.getDentist() == null || !appointment.getDentist().getId().equals(dentist.getId())) {
            throw new InvalidAppointmentStateException(String.format(
                    "Appointment with appointment id: %s is not for dentist with dentist id: %s", appointmentId,
                    dentistId
                                                                    ));
        }

        if(appointment.getAppointmentStatus() != AppointmentStatus.SCHEDULED){
            throw new InvalidAppointmentStateException(String.format("Appointment with appointment id: %s is not in " +
                                                                     "SCHEDULED state", appointment.getAppointmentId()));
        }

        appointment.setAppointmentStatus(appointmentStatus);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.appointmentToAppointmentResponseDTO(savedAppointment);

    }
}
