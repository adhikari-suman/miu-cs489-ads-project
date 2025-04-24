package edu.miu.cs489.adswebapp.respository;

import edu.miu.cs489.adswebapp.model.Appointment;
import edu.miu.cs489.adswebapp.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findByAppointmentId(String appointmentId);


    int countByDentistIdAndAppointmentStatusInAndAppointmentDateTimeBetween(Integer dentistId,
                                                                         AppointmentStatus[] statuses, LocalDateTime startDate, LocalDateTime endDate);

}
