package edu.miu.cs489.adswebapp.model;

import edu.miu.cs489.adswebapp.security.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "dentists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("DENTIST")
public class Dentist extends User {
    public static final String DENTIST_ID_PREFIX = "DENT-";

    @Column(name = "dentist_id", unique = true, nullable = false, length = 100)
    private String dentistId;

    @Column(name = "specialization", nullable = false, length = 100)
    private String specialization;

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
