package edu.miu.cs489.adswebapp.respository;

import edu.miu.cs489.adswebapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
