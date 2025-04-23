package edu.miu.cs489.adswebapp.service;

import edu.miu.cs489.adswebapp.dto.request.PatientRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.model.Patient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {
    public Page<PatientResponseDTO> getAllPatients(int page, int size);
    public PatientResponseDTO  getPatientByPatientNo(String patientNo);
    public PatientResponseDTO addPatient(PatientRequestDTO patient);
    public PatientResponseDTO updatePatient(String patientNo, PatientRequestDTO patient);
    public void deletePatient(String patientNo);
    public Page<PatientResponseDTO> searchPatients(String searchString, int page, int size);

    public PatientResponseDTO getPatientByUsername(String username);
}
