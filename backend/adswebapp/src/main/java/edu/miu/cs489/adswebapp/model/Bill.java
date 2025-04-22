package edu.miu.cs489.adswebapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Same as appointment ID

    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL)
    private Appointment appointment;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "bill_status", nullable = false)
    private int billStatus = 0;
}
