package edu.miu.cs489.adswebapp.configuration;

import edu.miu.cs489.adswebapp.model.*;
import edu.miu.cs489.adswebapp.repository.*;
import edu.miu.cs489.adswebapp.security.model.Role;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
            Address address8 = new Address(null, "456 Elm St, Springfield");
            Address address9 = new Address(null, "789 Oak St, Springfield");


            // Surgeries
            Surgery s10 = surgeryRepository.save(new Surgery(null, "SURG-10", address1, "111-111-1111", null));
            Surgery s13 = surgeryRepository.save(new Surgery(null, "SURG-13", address2, "222-222-2222", null));
            Surgery s15 = surgeryRepository.save(new Surgery(null, "SURG-15", address3, "333-333-3333", null));


            // Dentists
            Dentist tony = new Dentist();
            tony.setFirstName("Tony");
            tony.setLastName("Smith");
            tony.setUsername("tsmith");
            tony.setDentistId("DENT-1");
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
            helen.setDentistId("DENT-2");
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
            robin.setDentistId("DENT-3");
            robin.setPassword(passwordEncoder.encode("pwd"));
            robin.setPhoneNumber("123-458");
            robin.setEmail("robin@dental.com");
            robin.setSpecialization("Orthodontics");
            robin.setRole(Role.DENTIST);
            dentistRepository.save(robin);

            // Patients
            Patient p100 = new Patient();
            p100.setPatientNo("PAT-100");
            p100.setFirstName("Gillian");
            p100.setLastName("White");
            p100.setUsername("gwhite");
            p100.setPassword(passwordEncoder.encode("pwd"));
            p100.setPhoneNumber("321-456");
            p100.setEmail("gillian@patients.com");
            p100.setDateOfBirth(parseLocalDate("01-Feb-90 10:00", formatter).toLocalDate()); // Feb 1, 1990
            p100.setAddress(address4);
            p100.setRole(Role.PATIENT);
            patientRepository.save(p100);

            Patient p105 = new Patient();
            p105.setPatientNo("PAT-105");
            p105.setFirstName("Jill");
            p105.setLastName("Bell");
            p105.setUsername("jbell");
            p105.setPassword(passwordEncoder.encode("pwd"));
            p105.setPhoneNumber("321-457");
            p105.setEmail("jill@patients.com");
            p105.setDateOfBirth(parseLocalDate("20-May-88 10:00", formatter).toLocalDate()); // Jun 20, 1988
            p105.setAddress(address8);
            p105.setRole(Role.PATIENT);
            patientRepository.save(p105);

            Patient p108 = new Patient();
            p108.setPatientNo("PAT-108");
            p108.setFirstName("Ian");
            p108.setLastName("MacKay");
            p108.setUsername("imackay");
            p108.setPassword(passwordEncoder.encode("pwd"));
            p108.setPhoneNumber("321-458");
            p108.setEmail("ian@patients.com");
            p108.setDateOfBirth(parseLocalDate("11-Nov-85 10:00", formatter).toLocalDate()); // Nov 11, 1985
            p108.setAddress(address6);
            p108.setRole(Role.PATIENT);
            patientRepository.save(p108);

            Patient p110 = new Patient();
            p110.setPatientNo("PAT-110");
            p110.setFirstName("John");
            p110.setLastName("Walker");
            p110.setUsername("jwalker");
            p110.setPassword(passwordEncoder.encode("pwd"));
            p110.setPhoneNumber("321-459");
            p110.setEmail("john@patients.com");
            p110.setDateOfBirth(parseLocalDate("14-Apr-87 10:00", formatter).toLocalDate()); // Apr 14, 1987
            p110.setAddress(address7);
            p110.setRole(Role.PATIENT);
            patientRepository.save(p110);

            Patient p115 = new Patient();
            p115.setPatientNo("PAT-115");
            p115.setFirstName("Alice");
            p115.setLastName("Johnson");
            p115.setUsername("ajohnson");
            p115.setPassword(passwordEncoder.encode("pwd"));
            p115.setPhoneNumber("321-460");
            p115.setEmail("alice@patients.com");
            p115.setDateOfBirth(parseLocalDate("15-May-95 10:00", formatter).toLocalDate()); // May 15, 1995
            p115.setAddress(address5);
            p115.setRole(Role.PATIENT);
            patientRepository.save(p115);

            Patient p120 = new Patient();
            p120.setPatientNo("PAT-120");
            p120.setFirstName("Bob");
            p120.setLastName("Williams");
            p120.setUsername("bwilliams");
            p120.setPassword(passwordEncoder.encode("pwd"));
            p120.setPhoneNumber("321-461");
            p120.setEmail("bob@patients.com");
            p120.setDateOfBirth(
                                parseLocalDate("10-Jul-90 10:00", formatter).toLocalDate()); // Jul 10, 1990
            p120.setAddress(address9);
            p120.setRole(Role.PATIENT);
            patientRepository.save(p120);

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

            Appointment appointment7 = new Appointment();
            appointment7.setPatient(p115);
            appointment7.setDentist(tony); // Assign the dentist for this appointment
            appointment7.setSurgery(s13); // Surgery for the appointment
            appointment7.setAppointmentId("APPT-7");
            appointment7.setAppointmentDateTime(parseLocalDate("25-Apr-25 10:00", formatter));
            appointment7.setAppointmentStatus(AppointmentStatus.COMPLETED);
            Bill bill7 = new Bill();
            bill7.setAppointment(appointment7);
            bill7.setAmount(BigDecimal.valueOf(200.00));
            bill7.setBillStatus(BillStatus.PAID); // Bill is paid
            appointment7.setBill(bill7);

            // Save appointment and bill

            Appointment appointment8 = new Appointment();
            appointment8.setPatient(p120);
            appointment8.setDentist(helen); // Assign the dentist for this appointment
            appointment8.setSurgery(s15); // Surgery for the appointment
            appointment8.setAppointmentId("APPT-8");
            appointment8.setAppointmentDateTime(parseLocalDate("26-Apr-25 14:00", formatter));
            appointment8.setAppointmentStatus(AppointmentStatus.COMPLETED);
            Bill bill8 = new Bill();
            bill8.setAppointment(appointment8);
            bill8.setAmount(BigDecimal.valueOf(250.00));
            bill8.setBillStatus(BillStatus.PAID); // Bill is paid
            appointment8.setBill(bill8);

             // Save appointment and bill

            // Save appointments and bills
            appointmentRepository.save(appointment1);
            appointmentRepository.save(appointment2);
            appointmentRepository.save(appointment3);
            appointmentRepository.save(appointment4);
            appointmentRepository.save(appointment5);
            appointmentRepository.save(appointment6);
            appointmentRepository.save(appointment7);
            appointmentRepository.save(appointment8);

            // Add 5 appointments for Tony (Scheduled or Completed)
            appointmentRepository.save(createAppointment("APPT-9", formatter, "21-Apr-25 10:00",
                                                         AppointmentStatus.SCHEDULED, tony, p100, s15, BigDecimal.valueOf(100.00), BillStatus.PENDING));
            appointmentRepository.save(createAppointment("APPT-10", formatter, "21-Apr-25 14:00",
                                                         AppointmentStatus.SCHEDULED, tony, p100, s15, BigDecimal.valueOf(120.00), BillStatus.PENDING));
            appointmentRepository.save(createAppointment("APPT-11", formatter, "22-Apr-25 10:00",
                                                         AppointmentStatus.COMPLETED, tony, p100, s15, BigDecimal.valueOf(130.00), BillStatus.PAID));
            appointmentRepository.save(createAppointment("APPT-12", formatter, "22-Apr-25 14:00",
                                                         AppointmentStatus.COMPLETED, tony, p100, s15, BigDecimal.valueOf(140.00), BillStatus.PAID));
            appointmentRepository.save(createAppointment("APPT-13", formatter, "23-Apr-25 10:00",
                                                         AppointmentStatus.COMPLETED, tony, p100, s15, BigDecimal.valueOf(150.00), BillStatus.PAID));

            // Add 5 appointments for Helen (Scheduled or Completed)
            appointmentRepository.save(createAppointment("APPT-14", formatter, "23-Apr-25 14:00",
                                                         AppointmentStatus.SCHEDULED, helen, p105, s10, BigDecimal.valueOf(110.00), BillStatus.PENDING));
            appointmentRepository.save(createAppointment("APPT-15", formatter, "24-Apr-25 10:00",
                                                         AppointmentStatus.SCHEDULED, helen, p105, s10, BigDecimal.valueOf(115.00), BillStatus.PENDING));
            appointmentRepository.save(createAppointment("APPT-16", formatter, "24-Apr-25 14:00",
                                                         AppointmentStatus.SCHEDULED, helen, p105, s10, BigDecimal.valueOf(125.00), BillStatus.PENDING));
            appointmentRepository.save(createAppointment("APPT-17", formatter, "25-Apr-25 10:00",
                                                         AppointmentStatus.COMPLETED, helen, p105, s10, BigDecimal.valueOf(135.00), BillStatus.PAID));
            appointmentRepository.save(createAppointment("APPT-18", formatter, "25-Apr-25 14:00",
                                                         AppointmentStatus.COMPLETED, helen, p105, s10, BigDecimal.valueOf(145.00), BillStatus.PAID));

            // Add 2 appointments for Robin (1 scheduled, 1 completed)
            appointmentRepository.save(createAppointment("APPT-19", formatter, "21-Apr-25 12:00",
                                                         AppointmentStatus.SCHEDULED, robin, p108, s13, BigDecimal.valueOf(155.00), BillStatus.PENDING));
            appointmentRepository.save(createAppointment("APPT-20", formatter, "22-Apr-25 12:00",
                                                         AppointmentStatus.COMPLETED, robin, p108, s13, BigDecimal.valueOf(165.00), BillStatus.PAID));

            // Remaining appointments as pending, no dentist assigned
            appointmentRepository.save(createAppointment("APPT-21", formatter, "23-Apr-25 12:00",
                                                         AppointmentStatus.PENDING, null, p110, s13, BigDecimal.ZERO, BillStatus.PENDING));
            appointmentRepository.save(createAppointment("APPT-22", formatter, "24-Apr-25 12:00",
                                                         AppointmentStatus.PENDING, null, p110, s13, BigDecimal.ZERO, BillStatus.PENDING));


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

    private Appointment createAppointment(String id, DateTimeFormatter formatter, String dateTimeStr,
                                          AppointmentStatus status, Dentist dentist, Patient patient,
                                          Surgery surgery, BigDecimal amount, BillStatus billStatus) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(id);
        appointment.setAppointmentDateTime(parseLocalDate(dateTimeStr, formatter));
        appointment.setAppointmentStatus(status);
        appointment.setDentist(dentist);
        appointment.setPatient(patient);
        appointment.setSurgery(surgery);

        Bill bill = new Bill();
        bill.setAmount(amount);
        bill.setBillStatus(billStatus);
        bill.setAppointment(appointment);

        appointment.setBill(bill);
        return appointment;
    }
}
