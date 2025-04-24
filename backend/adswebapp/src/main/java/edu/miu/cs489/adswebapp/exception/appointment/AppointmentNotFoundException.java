package edu.miu.cs489.adswebapp.exception.appointment;

public class AppointmentNotFoundException extends  RuntimeException{
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
