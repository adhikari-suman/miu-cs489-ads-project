package edu.miu.cs489.adswebapp.exception.appointment;

public class InvalidAppointmentStateException extends RuntimeException{
    public InvalidAppointmentStateException(String message) {
        super(message);
    }
}
