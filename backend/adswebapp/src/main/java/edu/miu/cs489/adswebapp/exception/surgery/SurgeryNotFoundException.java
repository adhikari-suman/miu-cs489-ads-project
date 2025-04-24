package edu.miu.cs489.adswebapp.exception.surgery;

public class SurgeryNotFoundException extends RuntimeException{
    public SurgeryNotFoundException(String message) {
        super(message);
    }
}
