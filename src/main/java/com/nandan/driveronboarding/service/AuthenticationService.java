package com.nandan.driveronboarding.service;

import com.nandan.driveronboarding.enums.*;
import com.nandan.driveronboarding.exception.RegistrationException;
import com.nandan.driveronboarding.requests.RegisterRequest;
import com.nandan.driveronboarding.requests.AuthenticationRequest;
import com.nandan.driveronboarding.responses.AuthenticationResponse;
import com.nandan.driveronboarding.entities.Token;
import com.nandan.driveronboarding.repository.TokenRepository;
import com.nandan.driveronboarding.entities.User;
import com.nandan.driveronboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user with the Driver Onboarding system.
     *
     * @param request the RegisterRequest object containing user registration information
     * @throws RegistrationException if there is an error during the registration process
     */
    public void register(RegisterRequest request) throws RegistrationException {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .trackingDeviceStatus(TrackingDeviceStatus.NOT_ASSIGNED)
                .driverRegistrationStatus(DriverRegistrationStatus.INACTIVE)
                .driverRideAvailabilityStatus(DriverRideAvailabilityStatus.INACTIVE)
                .build();
        try {
            var savedUser = repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            saveUserToken(savedUser, jwtToken);
        }
        // catching as exception to save time. This could be handled based on hierarchy of which exception is thrown
        catch (Exception exception) {
            throw new RegistrationException(exception.getMessage());
        }
    }

    /**
     * Authenticates a user with the Driver Onboarding system.
     *
     * @param request the AuthenticationRequest object containing user authentication information
     * @return an AuthenticationResponse object containing the JWT token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Saves a new token for a user.
     *
     * @param user     the User object for the user
     * @param jwtToken the JWT token to save
     */
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Revokes all valid tokens for a user.
     *
     * @param user the User object for the user
     */
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
