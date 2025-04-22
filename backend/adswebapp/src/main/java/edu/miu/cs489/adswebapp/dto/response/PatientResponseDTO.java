package edu.miu.cs489.adswebapp.dto.response;

public record PatientResponseDTO(
        String patientNo,
        String firstName,
        String lastName,
        AddressResponseDTO address
        ) {
}
