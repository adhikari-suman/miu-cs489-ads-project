package edu.miu.cs489.adswebapp.dto.response;

import edu.miu.cs489.adswebapp.model.Address;

public record SurgeryResponseDTO(
        Integer id,
        String surgeryNo,
        AddressResponseDTO address,
        String phoneNumber
) {
}
