package edu.miu.cs489.adswebapp.dto.response;

import edu.miu.cs489.adswebapp.model.Appointment;
import edu.miu.cs489.adswebapp.model.BillStatus;

import java.math.BigDecimal;

public record BillResponseDTO(
        Integer id,
        BigDecimal amount,
        BillStatus billStatus
) {
}
