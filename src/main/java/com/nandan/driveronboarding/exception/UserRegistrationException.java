package com.nandan.driveronboarding.exception;

public class UserRegistrationException extends RuntimeException{
    public UserRegistrationException() {
        super();
    }

    public UserRegistrationException(String message) {
        super(message);
    }
}
