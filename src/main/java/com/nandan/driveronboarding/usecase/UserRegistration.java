package com.nandan.driveronboarding.usecase;

import com.nandan.driveronboarding.entities.User;
import com.nandan.driveronboarding.entities.UserContextHolder;
import com.nandan.driveronboarding.repository.UserRepository;
import com.nandan.driveronboarding.requests.AuthenticationRequest;
import com.nandan.driveronboarding.requests.DriverRideStatusRequest;
import com.nandan.driveronboarding.requests.RegisterRequest;
import com.nandan.driveronboarding.responses.AuthenticationResponse;
import com.nandan.driveronboarding.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRegistration {
    @Autowired
    AuthenticationService service;

    @Autowired
    UserRepository userRepository;

    public void registerUser(RegisterRequest request){
        service.register(request);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        return service.authenticate(request);
    }

    public String getDeviceStatus() {
        Long userId = UserContextHolder.getContext().getUserId();
        User user = userRepository.findById(userId).get();
        return user.getTrackingDeviceStatus().name();
    }

    public void updateDriverStatus(DriverRideStatusRequest driverRideStatusRequest) {
        Long userId = UserContextHolder.getContext().getUserId();
        User user = userRepository.findById(userId).get();
        user.setDriverRideAvailabilityStatus(driverRideStatusRequest.getStatus());
        userRepository.save(user);
    }
}
