package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.request.PatientRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.exception.patient.DuplicatePatientFoundException;
import edu.miu.cs489.adswebapp.exception.patient.PatientNotFoundException;
import edu.miu.cs489.adswebapp.mapper.PatientMapper;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.respository.PatientRepository;
import edu.miu.cs489.adswebapp.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper     patientMapper;

    @Override
    public Page<PatientResponseDTO> getAllPatients(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);


        Page<Patient> patients = patientRepository.findAll(pageRequest);

        return patients.map(patientMapper::patientToPatientResponseDTO);
    }

    @Override
    public PatientResponseDTO getPatientByPatientNo(String patientNo) {
        Patient patient = patientRepository.findByPatientNoEqualsIgnoreCase(patientNo)
                                           .orElseThrow(() -> new PatientNotFoundException(
                                                   String.format("No patient found with patient no: %s", patientNo)));

        return patientMapper.patientToPatientResponseDTO(patient);

    }

    @Override
    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO) {
        String patientNo = patientRequestDTO.patientNo();

        patientRepository.findByPatientNoEqualsIgnoreCase(patientNo).ifPresent((a) -> {
            throw new DuplicatePatientFoundException(
                    String.format("A patient with patient no " + "%s " + "already exists.", patientNo));
        });

        Patient newPatient = patientMapper.patientRequestDTOtoPatient(patientRequestDTO);
        Patient savedPatient = patientRepository.save(newPatient);

        return patientMapper.patientToPatientResponseDTO(savedPatient);
    }

    @Override
    public PatientResponseDTO updatePatient(String patientNo, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findByPatientNoEqualsIgnoreCase(patientNo)
                                           .orElseThrow(() -> new PatientNotFoundException(
                                                   String.format("No patient found with patient no: %s", patientNo)));

        Patient updatedPatient = patientMapper.patientRequestDTOtoPatient(patientRequestDTO);
        updatedPatient.setPatientNo(patient.getPatientNo());
        updatedPatient.setId(patient.getId());

       Patient savedPatient = patientRepository.save(updatedPatient);
       return patientMapper.patientToPatientResponseDTO(savedPatient);
    }

    @Override
    public void deletePatient(String patientNo) {
        Patient patient = patientRepository.findByPatientNoEqualsIgnoreCase(patientNo)
                                           .orElseThrow(() -> new PatientNotFoundException(
                                                   String.format("No patient found with patient no: %s", patientNo)));

        patientRepository.delete(patient);
    }

    @Override
    public Page<PatientResponseDTO> searchPatients(String searchString, int page, int size) {
        return patientRepository.findAllBySearchString(searchString, PageRequest.of(page,size))
                .map(patientMapper::patientToPatientResponseDTO);
    }
}
