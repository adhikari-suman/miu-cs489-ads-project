package edu.miu.cs489.adswebapp.configuration;

import edu.miu.cs489.adswebapp.model.*;
import edu.miu.cs489.adswebapp.respository.*;
import edu.miu.cs489.adswebapp.security.model.Role;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
@Transactional
public class DataInitializer {


    private final AppointmentService appointmentService;

    @Bean
    CommandLineRunner initData(
            AddressRepository addressRepository,
            SurgeryRepository surgeryRepository,
            PatientRepository patientRepository,
            DentistRepository dentistRepository,
            AppointmentRepository appointmentRepository,
            BillRepository billRepository,
            // Inject BillRepository to save Bill
            PasswordEncoder passwordEncoder,
            UserRepository userRepository
                              ) {
        return args -> {
            if (addressRepository.size() > 0) {
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm");

            // Addresses
            Address address1 = new Address(null, "123 Main St, Springfield");
            Address address2 = new Address(null, "456 Elm St, Springfield");
            Address address3 = new Address(null, "789 Oak St, Springfield");
            Address address4 = new Address(null, "123 Main St, Springfield");
            Address address5 = new Address(null, "456 Elm St, Springfield");
            Address address6 = new Address(null, "789 Oak St, Springfield");
            Address address7 = new Address(null, "123 Main St, Springfield");


            // Surgeries
            Surgery s10 = surgeryRepository.save(new Surgery(null, "S10", address1, "111-111-1111", null));
            Surgery s13 = surgeryRepository.save(new Surgery(null, "S13", address2, "222-222-2222", null));
            Surgery s15 = surgeryRepository.save(new Surgery(null, "S15", address3, "333-333-3333", null));


            // Dentists
            Dentist tony = new Dentist();
            tony.setFirstName("Tony");
            tony.setLastName("Smith");
            tony.setUsername("tsmith");
            tony.setPassword(passwordEncoder.encode("pwd"));
            tony.setPhoneNumber("123-456");
            tony.setEmail("tony@dental.com");
            tony.setSpecialization("General Dentistry");
            tony.setRole(Role.DENTIST);
            dentistRepository.save(tony);

            Dentist helen = new Dentist();
            helen.setFirstName("Helen");
            helen.setLastName("Pearson");
            helen.setUsername("hpearson");
            helen.setPassword(passwordEncoder.encode("pwd"));
            helen.setPhoneNumber("123-457");
            helen.setEmail("helen@dental.com");
            helen.setSpecialization("Oral Surgery");
            helen.setRole(Role.DENTIST);
            dentistRepository.save(helen);

            Dentist robin = new Dentist();
            robin.setFirstName("Robin");
            robin.setLastName("Plevin");
            robin.setUsername("rplevin");
            robin.setPassword(passwordEncoder.encode("pwd"));
            robin.setPhoneNumber("123-458");
            robin.setEmail("robin@dental.com");
            robin.setSpecialization("Orthodontics");
            robin.setRole(Role.DENTIST);
            dentistRepository.save(robin);

            // Patients
            Patient p100 = new Patient();
            p100.setPatientNo("P100");
            p100.setFirstName("Gillian");
            p100.setLastName("White");
            p100.setUsername("gwhite");
            p100.setPassword(passwordEncoder.encode("pwd"));
            p100.setPhoneNumber("321-456");
            p100.setEmail("gillian@patients.com");
            p100.setDateOfBirth(new Date(90, 1, 1)); // Feb 1, 1990
            p100.setAddress(address4);
            p100.setRole(Role.PATIENT);
            patientRepository.save(p100);

            Patient p105 = new Patient();
            p105.setPatientNo("P105");
            p105.setFirstName("Jill");
            p105.setLastName("Bell");
            p105.setUsername("jbell");
            p105.setPassword(passwordEncoder.encode("pwd"));
            p105.setPhoneNumber("321-457");
            p105.setEmail("jill@patients.com");
            p105.setDateOfBirth(new Date(88, 5, 20)); // Jun 20, 1988
            p105.setAddress(address5);
            p105.setRole(Role.PATIENT);
            patientRepository.save(p105);

            Patient p108 = new Patient();
            p108.setPatientNo("P108");
            p108.setFirstName("Ian");
            p108.setLastName("MacKay");
            p108.setUsername("imackay");
            p108.setPassword(passwordEncoder.encode("pwd"));
            p108.setPhoneNumber("321-458");
            p108.setEmail("ian@patients.com");
            p108.setDateOfBirth(new Date(85, 10, 11)); // Nov 11, 1985
            p108.setAddress(address6);
            p108.setRole(Role.PATIENT);
            patientRepository.save(p108);

            Patient p110 = new Patient();
            p110.setPatientNo("P110");
            p110.setFirstName("John");
            p110.setLastName("Walker");
            p110.setUsername("jwalker");
            p110.setPassword(passwordEncoder.encode("pwd"));
            p110.setPhoneNumber("321-459");
            p110.setEmail("john@patients.com");
            p110.setDateOfBirth(new Date(87, 3, 14)); // Apr 14, 1987
            p110.setAddress(address7);
            p110.setRole(Role.PATIENT);
            patientRepository.save(p110);

//            // Create Appointments and Bills using no-args constructor
            Appointment appointment1 = new Appointment();
            appointment1.setPatient(p100);
            appointment1.setDentist(tony);
            appointment1.setSurgery(s15);
            appointment1.setAppointmentId("APPT-1");
            appointment1.setAppointmentDateTime(parseLocalDate("12-Sep-13 10:00", formatter));
            appointment1.setAppointmentStatus(AppointmentStatus.SCHEDULED);
            Bill bill1 = new Bill();
            bill1.setAppointment(appointment1);
            bill1.setAmount(BigDecimal.valueOf(100.00));
            bill1.setBillStatus(BillStatus.PENDING);
            appointment1.setBill(bill1);

            Appointment appointment2 = new Appointment();
            appointment2.setPatient(p105);
            appointment2.setDentist(tony);
            appointment2.setSurgery(s15);
            appointment2.setAppointmentId("APPT-2");
            appointment2.setAppointmentDateTime(parseLocalDate("12-Sep-13 12:00", formatter));
            appointment2.setAppointmentStatus(AppointmentStatus.COMPLETED);
            Bill bill2 = new Bill();
            bill2.setAppointment(appointment2);
            bill2.setAmount(BigDecimal.valueOf(150.00));
            bill2.setBillStatus(BillStatus.PAID);
            appointment2.setBill(bill2);

            Appointment appointment3 = new Appointment();
            appointment3.setPatient(p108);
            appointment3.setDentist(helen);
            appointment3.setSurgery(s10);
            appointment3.setAppointmentId("APPT-3");
            appointment3.setAppointmentDateTime(parseLocalDate("12-Sep-13 10:00", formatter));
            appointment3.setAppointmentStatus(AppointmentStatus.COMPLETED);
            Bill bill3 = new Bill();
            bill3.setAppointment(appointment3);
            bill3.setAmount(BigDecimal.valueOf(200.00));
            bill3.setBillStatus(BillStatus.PAID);
            appointment3.setBill(bill3);

            Appointment appointment4 = new Appointment();
            appointment4.setPatient(p108);
            appointment4.setDentist(helen);
            appointment4.setSurgery(s10);
            appointment4.setAppointmentId("APPT-4");
            appointment4.setAppointmentDateTime(parseLocalDate("14-Sep-13 14:00", formatter));
            appointment4.setAppointmentStatus(AppointmentStatus.CANCELLED);
            Bill bill4 = new Bill();
            bill4.setAppointment(appointment4);
            bill4.setAmount(BigDecimal.valueOf(250.00));
            bill4.setBillStatus(BillStatus.PAID);
            appointment4.setBill(bill4);

            Appointment appointment5 = new Appointment();
            appointment5.setPatient(p105);
            appointment5.setDentist(robin);
            appointment5.setSurgery(s15);
            appointment5.setAppointmentId("APPT-5");
            appointment5.setAppointmentDateTime(parseLocalDate("14-Sep-13 16:30", formatter));
            appointment5.setAppointmentStatus(AppointmentStatus.SCHEDULED);
            Bill bill5 = new Bill();
            bill5.setAppointment(appointment5);
            bill5.setAmount(BigDecimal.valueOf(300.00));
            bill5.setBillStatus(BillStatus.PENDING);
            appointment5.setBill(bill5);

            Appointment appointment6 = new Appointment();
            appointment6.setPatient(p110);
            appointment6.setDentist(robin);
            appointment6.setSurgery(s13);
            appointment6.setAppointmentId("APPT-6");
            appointment6.setAppointmentDateTime(parseLocalDate("15-Sep-13 18:00", formatter));
            appointment6.setAppointmentStatus(AppointmentStatus.COMPLETED);
            Bill bill6 = new Bill();
            bill6.setAppointment(appointment6);
            bill6.setAmount(BigDecimal.valueOf(350.00));
            bill6.setBillStatus(BillStatus.PAID);
            appointment6.setBill(bill6);

            // Save appointments and bills
            appointmentRepository.save(appointment1);
            appointmentRepository.save(appointment2);
            appointmentRepository.save(appointment3);
            appointmentRepository.save(appointment4);
            appointmentRepository.save(appointment5);
            appointmentRepository.save(appointment6);

            // Office Admin
            OfficeManager officeManager = new OfficeManager();

           officeManager.setFirstName("Office");
           officeManager.setLastName("Admin");
           officeManager.setUsername("admin");
           officeManager.setPassword(passwordEncoder.encode("admin"));
           officeManager.setPhoneNumber("124-321-2459");
           officeManager.setEmail("john@officeAdmin.com");
           officeManager.setRole(Role.OFFICE_ADMIN);
           userRepository.save(officeManager);
        };
    }

    private LocalDateTime parseLocalDate(String dateStr, DateTimeFormatter formatter) {
        try {
            return LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date: " + dateStr, e);
        }
    }
}
