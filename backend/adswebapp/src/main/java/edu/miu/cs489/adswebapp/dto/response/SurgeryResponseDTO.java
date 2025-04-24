package edu.miu.cs489.adswebapp.dto.response;

public record SurgeryResponseDTO(
        Integer id,
        String surgeryNo,
        AddressResponseDTO address,
        String phoneNumber
) {
}
