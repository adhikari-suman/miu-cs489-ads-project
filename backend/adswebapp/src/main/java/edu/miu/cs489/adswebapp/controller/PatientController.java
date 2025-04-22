package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.request.PatientRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
           @RequestParam(value = "page", defaultValue = "0") int page,

           @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(patientService.getAllPatients(page, size));
    }

    @GetMapping("/{patientNo}")
    public ResponseEntity<PatientResponseDTO> getPatientByPatientNo(@PathVariable("patientNo") String patientNo) {
        return ResponseEntity.ok(patientService.getPatientByPatientNo(patientNo));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> addPatient(@RequestBody @Valid PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patient = patientService.addPatient(patientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @PutMapping("/{patientNo}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable("patientNo") String patientNo,
            @RequestBody @Valid PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patient = patientService.updatePatient( patientNo, patientRequestDTO);

        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{patientNo}")
    public ResponseEntity<Void> deletePatientByPatientNo(@PathVariable("patientNo") String patientNo) {
        patientService.deletePatient(patientNo);

        return ResponseEntity.noContent().build();
    }

    // http://localhost:8080/adsweb/api/v1/patient/search/{searchString} -
    // Queries all the Patient data for the patient(s) whose data matches the input searchString.
    @GetMapping("/search/{searchString}")
    public ResponseEntity<Page<PatientResponseDTO>> searchPatients(
            @PathVariable("searchString") String searchString,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(patientService.searchPatients(searchString, page, size));
    }
}
