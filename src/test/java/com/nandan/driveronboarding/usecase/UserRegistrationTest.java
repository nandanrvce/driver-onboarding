package com.nandan.driveronboarding.usecase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nandan.driveronboarding.enums.DriverRideAvailabilityStatus;
import com.nandan.driveronboarding.enums.TrackingDeviceStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.nandan.driveronboarding.entities.User;
import com.nandan.driveronboarding.entities.UserContextHolder;
import com.nandan.driveronboarding.repository.UserRepository;
import com.nandan.driveronboarding.requests.AuthenticationRequest;
import com.nandan.driveronboarding.requests.DriverRideStatusRequest;
import com.nandan.driveronboarding.requests.RegisterRequest;
import com.nandan.driveronboarding.responses.AuthenticationResponse;
import com.nandan.driveronboarding.service.AuthenticationService;

import java.util.Optional;

public class UserRegistrationTest {

    @Mock
    AuthenticationService authServiceMock;

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    UserRegistration userRegistration;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test registerUser method")
    public void testRegisterUser() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("password");
        registerRequest.setEmail("testuser");
        // Mock the service method
        doNothing().when(authServiceMock).register(registerRequest);

        // Call the method under test
        userRegistration.registerUser(registerRequest);

        // Verify that the service method was called once
        verify(authServiceMock, times(1)).register(registerRequest);
    }

    @Test
    @DisplayName("Test authenticate method")
    public void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("testuser", "password");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token");

        // Mock the service method
        when(authServiceMock.authenticate(authenticationRequest)).thenReturn(expectedResponse);

        // Call the method under test
        AuthenticationResponse actualResponse = userRegistration.authenticate(authenticationRequest);

        // Verify that the service method was called once
        verify(authServiceMock, times(1)).authenticate(authenticationRequest);

        // Verify that the response matches the expected response
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test getDeviceStatus method")
    public void testGetDeviceStatus() {
        Long userId = 1L;
        User user = new User();
        user.setPassword("password");
        user.setDriverRideAvailabilityStatus(DriverRideAvailabilityStatus.ACTIVE);
        user.setTrackingDeviceStatus(TrackingDeviceStatus.DELIVERED);
        UserContextHolder.getContext().setUserId(userId);

        // Mock the repository method
        when(userRepositoryMock.findById(userId)).thenReturn(java.util.Optional.of(user));
        // Call the method under test
        String deviceStatus = userRegistration.getDeviceStatus();

        // Verify that the repository method was called once
        verify(userRepositoryMock, times(1)).findById(userId);

        // Verify that the device status matches the user's tracking device status
        assertEquals(user.getTrackingDeviceStatus().name(), deviceStatus);
    }

    @Test
    @DisplayName("Test updateDriverStatus method")
    public void testUpdateDriverStatus() {
        Long userId = 1L;
        User user = new User();
        user.setPassword("password");
        user.setDriverRideAvailabilityStatus(DriverRideAvailabilityStatus.INACTIVE);
        user.setTrackingDeviceStatus(TrackingDeviceStatus.DELIVERED);
        UserContextHolder.getContext().setUserId(userId);

        DriverRideStatusRequest driverRideStatusRequest = new DriverRideStatusRequest();
        driverRideStatusRequest.setStatus(DriverRideAvailabilityStatus.INACTIVE);
        // Mock the repository method
        when(userRepositoryMock.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(userRepositoryMock.save(user)).thenReturn(user);

        // Call the method under test
        userRegistration.updateDriverStatus(driverRideStatusRequest);
        verify(userRepositoryMock).save(user);
    }
}