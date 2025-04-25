package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.request.PatientRequestAppointmentRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.PatientResponseDTO;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import edu.miu.cs489.adswebapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "patientNo") String sortBy,
            @RequestParam(defaultValue = "asc") String order
                                                                  ) {
        Page<PatientResponseDTO> patientResponseDTOs = patientService.getAllPatients(page, size, sortBy, order);

        return ResponseEntity.ok(patientResponseDTOs);
    }

    @GetMapping("/{patientNo}")
    public ResponseEntity<PatientResponseDTO> getPatientByPatientNo(@PathVariable("patientNo") String patientNo) {
        return ResponseEntity.ok(patientService.getPatientByPatientNo(patientNo));
    }

    @GetMapping("/{patientNo}/appointments")
    public ResponseEntity<Page<AppointmentResponseDTO>> getAppointmentsOfPatientByPatientNo(
            @PathVariable("patientNo") String patientNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentId") String sortBy,
            @RequestParam(defaultValue = "asc") String order
                                                                                           ) {

        Page<AppointmentResponseDTO> appointmentResponseDTOs = appointmentService.getAppointmentsForPatientByPatientNo(
                patientNo, page, size, sortBy, order);

        return ResponseEntity.ok(appointmentResponseDTOs);
    }

    @PostMapping("/{patientNo}/appointments")
    public ResponseEntity<AppointmentResponseDTO> makeAppointmentForPatientByPatientNo(
            @PathVariable("patientNo") String patientNo,
            @Validated @RequestBody PatientRequestAppointmentRequestDTO patientRequestAppointmentRequestDTO
                                                                      ) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.requestAppointmentForPatient(patientNo,
                                                                               patientRequestAppointmentRequestDTO);


        return ResponseEntity.ok(appointmentResponseDTO);
    }

    @PostMapping("/{patientNo}/appointments/{appointmentId}/pay")
    public ResponseEntity<Void> payForAppointmentForPatientByPatientNo(
            @PathVariable("patientNo") String patientNo,
            @PathVariable("appointmentId")
            String appointmentId
                                                                        ) {
        appointmentService.payBillForPatient(appointmentId, patientNo);

        return ResponseEntity.noContent().build();
    }
}
