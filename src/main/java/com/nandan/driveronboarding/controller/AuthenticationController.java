package com.nandan.driveronboarding.controller;

import com.nandan.driveronboarding.requests.AuthenticationRequest;
import com.nandan.driveronboarding.responses.AuthenticationResponse;
import com.nandan.driveronboarding.requests.RegisterRequest;
import com.nandan.driveronboarding.usecase.UserRegistration;
import com.nandan.driveronboarding.util.ApiConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nandan.driveronboarding.util.ApiConstant.AUTH_ENDPOINT;

/**
 * Controller class to handle authentication endpoints for user registration and authentication.
 */
@RestController
@RequestMapping(AUTH_ENDPOINT)
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    UserRegistration userRegistration;

    /**
     * Endpoint to register a new user.
     *
     * @param request The request object containing user registration details.
     * @return HTTP response entity.
     */
    @PostMapping(ApiConstant.SIGNUP_ENDPOINT)
    public ResponseEntity register(
            @RequestBody RegisterRequest request
    ) {
        userRegistration.registerUser(request);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Endpoint to authenticate an existing user.
     *
     * @param request The request object containing user authentication details.
     * @return HTTP response entity with the authentication response object.
     */
    @PostMapping(ApiConstant.AUTHENTICATE_ENDPOINT)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userRegistration.authenticate(request));
    }

}
