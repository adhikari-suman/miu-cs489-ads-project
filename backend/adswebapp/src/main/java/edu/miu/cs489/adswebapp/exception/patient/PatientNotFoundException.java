package edu.miu.cs489.adswebapp.exception.patient;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String message) {
        super(message);
    }
}
