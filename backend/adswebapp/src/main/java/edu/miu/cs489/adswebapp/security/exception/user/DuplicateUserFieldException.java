package edu.miu.cs489.adswebapp.security.exception.user;

public class DuplicateUserFieldException extends RuntimeException{
    public DuplicateUserFieldException(String message) {
        super(message);
    }
}
