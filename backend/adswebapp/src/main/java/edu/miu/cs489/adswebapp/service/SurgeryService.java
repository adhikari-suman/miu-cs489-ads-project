package edu.miu.cs489.adswebapp.service;

import edu.miu.cs489.adswebapp.dto.response.SurgeryResponseDTO;
import org.springframework.data.domain.Page;

public interface SurgeryService {
    Page<SurgeryResponseDTO> getAllSurgeries(int page, int size,
                                             String sortBy, String sortDirection
                                             );

    SurgeryResponseDTO getSurgeryBySurgeryNo(String surgeryNo);
}
