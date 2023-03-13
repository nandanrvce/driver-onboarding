package com.nandan.driveronboarding.service;

import com.nandan.driveronboarding.entities.Token;
import com.nandan.driveronboarding.entities.User;
import com.nandan.driveronboarding.enums.Role;
import com.nandan.driveronboarding.exception.RegistrationException;
import com.nandan.driveronboarding.repository.TokenRepository;
import com.nandan.driveronboarding.repository.UserRepository;
import com.nandan.driveronboarding.requests.AuthenticationRequest;
import com.nandan.driveronboarding.requests.RegisterRequest;
import com.nandan.driveronboarding.responses.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testRegister() throws RegistrationException {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password");

        User user = User.builder()
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token");

        authenticationService.register(registerRequest);

        verify(userRepository, times(1)).save(user);
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void testRegisterThrowsRegistrationException() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password");

        User user = User.builder()
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        when(userRepository.save(user)).thenThrow(new RuntimeException());

        assertThrows(RegistrationException.class, () -> authenticationService.register(registerRequest));

        verify(userRepository, times(1)).save(user);
        verify(jwtService, never()).generateToken(user);
        verify(tokenRepository, never()).save(any(Token.class));
    }
}