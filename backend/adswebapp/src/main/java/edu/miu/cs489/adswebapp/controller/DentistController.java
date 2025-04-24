package edu.miu.cs489.adswebapp.controller;


import edu.miu.cs489.adswebapp.dto.request.DentistRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.AppointmentResponseDTO;
import edu.miu.cs489.adswebapp.dto.response.DentistResponseDTO;
import edu.miu.cs489.adswebapp.model.AppointmentStatus;
import edu.miu.cs489.adswebapp.service.AppointmentService;
import edu.miu.cs489.adswebapp.service.DentistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dentists")
@RequiredArgsConstructor
public class DentistController {

    private final DentistService dentistService;
    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<Page<DentistResponseDTO>> getAllDentists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dentistId") String sortBy,
            @RequestParam(defaultValue = "asc") String order
                                                                  ) {
        return ResponseEntity.ok(dentistService.getAllDentists(page, size, sortBy, order));
    }

    @PostMapping
    public ResponseEntity<Object> addADentist(@RequestBody @Validated DentistRequestDTO dentistRequestDTO) {
        DentistResponseDTO dentistResponseDTO = dentistService.addADentist(dentistRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(dentistResponseDTO);
    }

    @GetMapping("/{dentistId}")
    public ResponseEntity<DentistResponseDTO> getADentistByDentistId(@PathVariable("dentistId") String dentistId) {
        return ResponseEntity.ok(dentistService.getDentistByDentistId(dentistId));
    }

    @GetMapping("/{dentistId}/appointments")
    public ResponseEntity<Page<AppointmentResponseDTO>> getAppointmentsForDentistByDentistId(
            @PathVariable("dentistId") String dentistId,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentId") String sortBy,
            @RequestParam(defaultValue = "asc") String order
                                                                                            ) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentsForDentistByDentistId(dentistId, page, size, sortBy, order));

    }

    @GetMapping("/{dentistId}/appointments/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentForDentistByDentistIdAndAppointmentId(
            @PathVariable("dentistId") String dentistId,
            @PathVariable("appointmentId") String appointmentId
                                                                                                     ) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentByAppointmentIdAndDentistId(appointmentId, dentistId));
    }


    @PatchMapping("/{dentistId}/appointments/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointmentStatusForAppointmentByAppointmentId(
            @PathVariable("dentistId") String dentistId,
            @PathVariable("appointmentId") String appointmentId,
            @RequestParam("appointmentStatus") AppointmentStatus appointmentStatus

                                                                                                      ) {
        AppointmentResponseDTO
                appointmentResponseDTO
                = appointmentService.updateAppointStatusForDentistByDentistIdAndAppointmentId(
                dentistId, appointmentId, appointmentStatus);

        return ResponseEntity.ok(appointmentResponseDTO);
    }
}
