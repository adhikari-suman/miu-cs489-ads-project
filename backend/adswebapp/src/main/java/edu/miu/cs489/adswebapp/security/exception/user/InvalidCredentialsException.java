package edu.miu.cs489.adswebapp.security.exception.user;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
