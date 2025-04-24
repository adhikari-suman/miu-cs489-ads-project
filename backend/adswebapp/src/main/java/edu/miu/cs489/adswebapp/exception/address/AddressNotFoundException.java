package edu.miu.cs489.adswebapp.exception.address;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(String message) {
        super(message);
    }
}
