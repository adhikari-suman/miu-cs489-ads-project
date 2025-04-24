package edu.miu.cs489.adswebapp.exception.dentist;

public class DentistNotFoundException extends RuntimeException{
    public DentistNotFoundException(String message) {
        super(message);
    }
}
