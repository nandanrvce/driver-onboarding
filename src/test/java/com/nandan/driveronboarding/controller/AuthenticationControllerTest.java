package com.nandan.driveronboarding.controller;

import com.nandan.driveronboarding.requests.AuthenticationRequest;
import com.nandan.driveronboarding.responses.AuthenticationResponse;
import com.nandan.driveronboarding.requests.RegisterRequest;
import com.nandan.driveronboarding.usecase.UserRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private UserRegistration userRegistration;

    @InjectMocks
    private AuthenticationController authenticationController;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    public void setUp() {
        registerRequest = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("johndoe@example.com")
                .password("password")
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .email("johndoe@example.com")
                .password("password")
                .build();
    }

    @Test
    public void register_shouldReturnOkResponse() {
        doNothing().when(userRegistration).registerUser(registerRequest);

        ResponseEntity responseEntity = authenticationController.register(registerRequest);

        verify(userRegistration, times(1)).registerUser(registerRequest);
        verifyNoMoreInteractions(userRegistration);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void authenticate_shouldReturnAuthenticationResponse() {
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("token")
                .build();

        when(userRegistration.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.authenticate(authenticationRequest);

        verify(userRegistration, times(1)).authenticate(authenticationRequest);
        verifyNoMoreInteractions(userRegistration);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("token", responseEntity.getBody().getToken());
    }
}
