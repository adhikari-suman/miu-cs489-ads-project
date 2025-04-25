package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.exception.patient.PatientNotFoundException;
import edu.miu.cs489.adswebapp.mapper.PatientMapper;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.repository.PatientRepository;
import edu.miu.cs489.adswebapp.repository.UserRepository;
import edu.miu.cs489.adswebapp.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper     patientMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<PatientResponseDTO> getAllPatients(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

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

//    @Override
//    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO) {
//        Patient patient = patientMapper.patientRequestDTOtoPatient(patientRequestDTO);
//        userRepository.findByEmail(patient.getEmail()).ifPresent((a) -> {
//            throw new DuplicateUserFieldException(String.format("Email %s already exists", patient.getEmail()));
//        });
//
//        userRepository.findByUsername(patient.getUsername()).ifPresent((a) -> {
//            throw new DuplicateUserFieldException(String.format("Username %s already exists", patient.getUsername()));
//        });
//
//        String nextPatientNo = null;
//
//        Optional<Patient> optionalPatient = patientRepository.findTopByOrderByIdDesc();
//
//        if(optionalPatient.isPresent()){
//            Patient topPatient = optionalPatient.get();
//
//            String currentPatientId =
//                    topPatient.getPatientNo().split(Patient.PATIENT_ID_PREFIX)[1];
//
//            nextPatientNo =
//                    Patient.PATIENT_ID_PREFIX + (Integer.parseInt(currentPatientId) + 1);
//        } else {
//            nextPatientNo =
//                    Patient.PATIENT_ID_PREFIX + 1;
//        }
//
//
//        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
//        patient.setPatientNo(nextPatientNo);
//        patient.setRole(Role.PATIENT);
//
//        Patient savedPatient = patientRepository.save(patient);
//
//        return patientMapper.patientToPatientResponseDTO(savedPatient);
//    }

//    @Override
//    public PatientResponseDTO updatePatient(String patientNo, PatientRequestDTO patientRequestDTO) {
//        Patient patient = patientRepository.findByPatientNoEqualsIgnoreCase(patientNo)
//                                           .orElseThrow(() -> new PatientNotFoundException(
//                                                   String.format("No patient found with patient no: %s", patientNo)));
//
//        Patient updatedPatient = patientMapper.patientRequestDTOtoPatient(patientRequestDTO);
//        updatedPatient.setPatientNo(patient.getPatientNo());
//        updatedPatient.setId(patient.getId());
//
//       Patient savedPatient = patientRepository.save(updatedPatient);
//       return patientMapper.patientToPatientResponseDTO(savedPatient);
//    }

//    @Override
//    public void deletePatient(String patientNo) {
//        Patient patient = patientRepository.findByPatientNoEqualsIgnoreCase(patientNo)
//                                           .orElseThrow(() -> new PatientNotFoundException(
//                                                   String.format("No patient found with patient no: %s", patientNo)));
//
//        patientRepository.delete(patient);
//    }

//    @Override
//    public Page<PatientResponseDTO> searchPatients(String searchString, int page, int size) {
//        return patientRepository.findAllBySearchString(searchString, PageRequest.of(page,size))
//                .map(patientMapper::patientToPatientResponseDTO);
//    }

    @Override
    public PatientResponseDTO getPatientByUsername(String username) {
        Optional<Patient> patient = patientRepository.findByUsername(username);
        if(patient.isPresent()) {
            return patientMapper.patientToPatientResponseDTO(patient.get());
        } else {
            throw new PatientNotFoundException(String.format("No patient found with username: %s", username));
        }
    }

}
