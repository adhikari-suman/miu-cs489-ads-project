package edu.miu.cs489.adswebapp.exception.surgery;

public class DuplicateSurgeryFoundException extends RuntimeException{
    public DuplicateSurgeryFoundException(String message) {
        super(message);
    }
}
