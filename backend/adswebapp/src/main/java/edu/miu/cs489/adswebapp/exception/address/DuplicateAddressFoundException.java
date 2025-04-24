package edu.miu.cs489.adswebapp.exception.address;

public class DuplicateAddressFoundException extends RuntimeException{
    public DuplicateAddressFoundException(String message) {
        super(message);
    }
}
