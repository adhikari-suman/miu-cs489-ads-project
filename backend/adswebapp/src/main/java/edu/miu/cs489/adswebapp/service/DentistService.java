package edu.miu.cs489.adswebapp.service;

import edu.miu.cs489.adswebapp.dto.request.DentistRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.DentistResponseDTO;
import org.springframework.data.domain.Page;

public interface DentistService {
    public Page<DentistResponseDTO> getAllDentists(int page, int size, String sortBy, String sortDirection);
    public DentistResponseDTO  getDentistByDentistId(String dentistId);
//    public DentistResponseDTO addDentist(DentistRequestDTO dentist);
//    public DentistResponseDTO updateDentist(String dentistNo, DentistRequestDTO dentist);
//    public void deleteDentist(String dentistNo);
//    public Page<DentistResponseDTO> searchDentists(String searchString, int page, int size);

    public DentistResponseDTO getDentistByUsername(String username);

    DentistResponseDTO addADentist(DentistRequestDTO dentistRequestDTO);
}
