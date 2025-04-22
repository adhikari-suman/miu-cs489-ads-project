package edu.miu.cs489.adswebapp.exception.patient;

public class DuplicatePatientFoundException extends RuntimeException{
    public DuplicatePatientFoundException(String message) {
        super(message);
    }
}
