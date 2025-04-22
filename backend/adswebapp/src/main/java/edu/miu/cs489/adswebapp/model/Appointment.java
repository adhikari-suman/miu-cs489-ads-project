package edu.miu.cs489.adswebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dentist_id")
    private Dentist dentist;

    @ManyToOne
    @JoinColumn(name = "surgery_id", nullable = false)
    private Surgery surgery;

    @Column(name = "appointment_date_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentDateTime;


    @Column(name = "appointment_status", nullable = false)
    private int appointmentStatus;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
