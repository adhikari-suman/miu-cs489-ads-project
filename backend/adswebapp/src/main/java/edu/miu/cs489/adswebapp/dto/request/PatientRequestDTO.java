package edu.miu.cs489.adswebapp.dto.request;

import java.util.Date;

public record PatientRequestDTO(String firstName,
                                String lastName,
                                String username,
                                String password,
                                String phoneNumber,
                                String email,
                                String patientNo,
                                AddressRequestDTO address,
                                Date dateOfBirth
//        List<AppointmentRequestDTO> appointments
) {
}