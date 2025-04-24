package edu.miu.cs489.adswebapp.exception;

import edu.miu.cs489.adswebapp.exception.appointment.AppointmentNotFoundException;
import edu.miu.cs489.adswebapp.exception.appointment.InvalidAppointmentStateException;
import edu.miu.cs489.adswebapp.exception.dentist.DentistNotFoundException;
import edu.miu.cs489.adswebapp.exception.patient.DuplicatePatientFoundException;
import edu.miu.cs489.adswebapp.exception.patient.PatientNotFoundException;
import edu.miu.cs489.adswebapp.exception.surgery.SurgeryNotFoundException;
import edu.miu.cs489.adswebapp.security.exception.user.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiError> handlePatientNotFoundException(PatientNotFoundException e) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(), null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(DuplicatePatientFoundException.class)
    public ResponseEntity<ApiError> handleDuplicatePatientFoundException(DuplicatePatientFoundException e) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> validationErrors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        });

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentialsException(InvalidCredentialsException e) {
        ApiError apiError = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(), null
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ApiError> handleAppointmentNotFoundException(AppointmentNotFoundException e) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(), null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(InvalidAppointmentStateException.class)
    public ResponseEntity<ApiError> handleInvalidAppointmentStateException(InvalidAppointmentStateException e) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(DentistNotFoundException.class)
    public ResponseEntity<ApiError> handleDentistNotFoundException(DentistNotFoundException e) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(), null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(SurgeryNotFoundException.class)
    public ResponseEntity<ApiError> handleSurgeryNotFoundException(SurgeryNotFoundException e) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(), null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e
                                                                                 ) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), e.getParameterValidationResults().toString(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
