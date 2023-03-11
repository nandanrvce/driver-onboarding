package com.nandan.driveronboarding.usecase;

import com.nandan.driveronboarding.requests.AuthenticationRequest;
import com.nandan.driveronboarding.requests.RegisterRequest;
import com.nandan.driveronboarding.responses.AuthenticationResponse;
import com.nandan.driveronboarding.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRegistration {
    @Autowired
    AuthenticationService service;

    public void registerUser(RegisterRequest request){
        service.register(request);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        return service.authenticate(request);
    }
}
