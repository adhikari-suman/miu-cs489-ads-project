package edu.miu.cs489.adswebapp.respository;

import aj.org.objectweb.asm.commons.Remapper;
import edu.miu.cs489.adswebapp.model.Appointment;
import edu.miu.cs489.adswebapp.model.AppointmentStatus;
import edu.miu.cs489.adswebapp.model.BillStatus;
import edu.miu.cs489.adswebapp.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(
            """
            SELECT COUNT(a) FROM Appointment a JOIN a.patient p JOIN a.bill b WHERE p.patientNo=:patientNo AND b.billStatus =:billStatus
            """
    )
    int countBillsForPatientByBillStatus(@Param("patientNo") String patientNo,
                                         @Param("billStatus")BillStatus billStatus);

    Optional<Appointment> findByAppointmentId(String appointmentId);

    int countByDentistIdAndAppointmentStatusInAndAppointmentDateTimeBetween(
            Integer dentistId,
            AppointmentStatus[] statuses, LocalDateTime startDate, LocalDateTime endDate
                                                                           );

    Page<Appointment> findAllBySurgeryId(Integer surgeryId, Pageable pageable);

    Page<Appointment> findAllByPatientId(Integer patientId, Pageable pageable);

    Optional<Appointment> findTopByOrderByIdDesc();

    Page<Appointment> findByDentistId(Integer dentistId, PageRequest attr0);
}
