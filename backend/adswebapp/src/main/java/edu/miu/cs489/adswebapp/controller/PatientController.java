package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    @GetMapping
    public ResponseEntity<List<Object>> getAllPatients() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{patientNo}")
    public ResponseEntity<Object> getPatientByPatientNo(@PathVariable("patientNo") String patientNo) {
        return ResponseEntity.ok("Patient with patient no: " + patientNo + " found.");
    }

    @PutMapping("/{patientNo}")
    public ResponseEntity<Object> updatePatient(@PathVariable("patientNo") String patientNo) {
        return ResponseEntity.ok("Patient with patient no: " + patientNo + " updated.");
    }

    @GetMapping("/{patientNo}/appointments")
    public ResponseEntity<Object> getAppointmentsOfPatientByPatientNo(@PathVariable("patientNo") String patientNo) {
        return ResponseEntity.ok("Appointments of patient with patient no: " + patientNo + " found.");
    }

    @PostMapping("/{patientNo}/appointments")
    public ResponseEntity<Object> makeAppointmentForPatientByPatientNo(@PathVariable("patientNo") String patientNo) {
        return ResponseEntity.ok("Appointment for patient with patient no: " + patientNo + " made.");
    }


    @PatchMapping("/{patientNo}/appointments/{appointmentId}")
    public ResponseEntity<Object> makeAppointmentForPatientByPatientNo(
            @PathVariable("patientNo") String patientNo,
            @PathVariable("appointmentId")
            String appointmentId
                                                                      ) {
        return ResponseEntity.ok("Appointment with appointment id: "+appointmentId+" for patient with patient no: " + patientNo +
                                 " cancelled.");
    }

    @PostMapping("/{patientNo}/appointments/{appointmentId}/pay")
    public ResponseEntity<Object> payForAppointmentForPatientByPatientNo(
            @PathVariable("patientNo") String patientNo,
            @PathVariable("appointmentId")
            String appointmentId
                                                                      ) {
        return ResponseEntity.ok(
                String.format("Payment for appointment Id: %s for patient %s is completed successfully.",
                              appointmentId, patientNo)
                                );
    }


//    @GetMapping
//    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
//           @RequestParam(value = "page", defaultValue = "0") int page,
//
//           @RequestParam(value = "size", defaultValue = "5") int size) {
//        return ResponseEntity.ok(patientService.getAllPatients(page, size));
//    }
//
//    @GetMapping("/{patientNo}")
//    public ResponseEntity<PatientResponseDTO> getPatientByPatientNo(@PathVariable("patientNo") String patientNo) {
//        return ResponseEntity.ok(patientService.getPatientByPatientNo(patientNo));
//    }
//
//    @PostMapping
//    public ResponseEntity<PatientResponseDTO> addPatient(@RequestBody @Valid PatientRequestDTO patientRequestDTO) {
//        PatientResponseDTO patient = patientService.addPatient(patientRequestDTO);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
//    }
//
//    @PutMapping("/{patientNo}")
//    public ResponseEntity<PatientResponseDTO> updatePatient(
//            @PathVariable("patientNo") String patientNo,
//            @RequestBody @Valid PatientRequestDTO patientRequestDTO) {
//        PatientResponseDTO patient = patientService.updatePatient( patientNo, patientRequestDTO);
//
//        return ResponseEntity.ok(patient);
//    }
//
//    @DeleteMapping("/{patientNo}")
//    public ResponseEntity<Void> deletePatientByPatientNo(@PathVariable("patientNo") String patientNo) {
//        patientService.deletePatient(patientNo);
//
//        return ResponseEntity.noContent().build();
//    }
//
//    // http://localhost:8080/adsweb/api/v1/patient/search/{searchString} -
//    // Queries all the Patient data for the patient(s) whose data matches the input searchString.
//    @GetMapping("/search/{searchString}")
//    public ResponseEntity<Page<PatientResponseDTO>> searchPatients(
//            @PathVariable("searchString") String searchString,
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "5") int size) {
//        return ResponseEntity.ok(patientService.searchPatients(searchString, page, size));
//    }
}
