package edu.miu.cs489.adswebapp.security.exception;

import edu.miu.cs489.adswebapp.exception.ApiError;
import edu.miu.cs489.adswebapp.security.exception.user.DuplicateUserFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(HttpStatus.UNAUTHORIZED.value(),
                                                                                e.getMessage(), null));
    }

    @ExceptionHandler(DuplicateUserFieldException.class)
    public ResponseEntity<ApiError> handleDuplicateUserFieldException(DuplicateUserFieldException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(HttpStatus.CONFLICT.value(),
                                                                               e.getMessage(), null));
    }
}
