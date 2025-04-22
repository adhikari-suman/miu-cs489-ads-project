package edu.miu.cs489.adswebapp.exception;

import edu.miu.cs489.adswebapp.exception.patient.DuplicatePatientFoundException;
import edu.miu.cs489.adswebapp.exception.patient.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiError> handlePatientNotFoundException(PatientNotFoundException e) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(DuplicatePatientFoundException.class)
    public ResponseEntity<ApiError> handleDuplicatePatientFoundException(DuplicatePatientFoundException e) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
