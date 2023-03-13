package com.nandan.driveronboarding.exception;

public class VehicleRegistrationException extends RuntimeException{
    public VehicleRegistrationException() {
        super();
    }

    public VehicleRegistrationException(String message) {
        super(message);
    }
}
