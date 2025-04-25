package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.response.SurgeryResponseDTO;
import edu.miu.cs489.adswebapp.exception.surgery.SurgeryNotFoundException;
import edu.miu.cs489.adswebapp.mapper.SurgeryMapper;
import edu.miu.cs489.adswebapp.model.Surgery;
import edu.miu.cs489.adswebapp.repository.SurgeryRepository;
import edu.miu.cs489.adswebapp.service.SurgeryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SurgeryServiceImpl implements SurgeryService {
    private final SurgeryRepository surgeryRepository;
    private final SurgeryMapper     surgeryMapper;


    @Override
    public Page<SurgeryResponseDTO> getAllSurgeries(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        return surgeryRepository.findAll(pageRequest).map(surgeryMapper::surgeryToSurgeryResponseDTO);
    }

    @Override
    public SurgeryResponseDTO getSurgeryBySurgeryNo(String surgeryNo) {
        Surgery surgery = surgeryRepository.findBySurgeryNo(surgeryNo)
                                           .orElseThrow(() -> new SurgeryNotFoundException(
                                                   String.format("No surgery found with surgery no: %s", surgeryNo)));

        return surgeryMapper.surgeryToSurgeryResponseDTO(surgery);
    }
}
