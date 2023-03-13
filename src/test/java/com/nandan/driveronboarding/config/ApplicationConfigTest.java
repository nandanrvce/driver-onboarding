package com.nandan.driveronboarding.config;

import com.nandan.driveronboarding.entities.User;
import com.nandan.driveronboarding.repository.UserRepository;
import com.nandan.driveronboarding.util.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void userDetailsService_shouldReturnUserDetailsService() {
        // When
        UserDetailsService result = applicationConfig.userDetailsService();

        // Then
        assertNotNull(result);
    }

    @Test
    void authenticationProvider_shouldReturnDaoAuthenticationProvider() {
        // When
        AuthenticationProvider result = applicationConfig.authenticationProvider();

        // Then
        assertNotNull(result);
    }

    @Test
    void authenticationManager_shouldReturnAuthenticationManager() throws Exception {
        // Given
        AuthenticationConfiguration authConfig = mock(AuthenticationConfiguration.class);
        AuthenticationManager authManager = mock(AuthenticationManager.class);
        when(authConfig.getAuthenticationManager()).thenReturn(authManager);

        // When
        AuthenticationManager result = applicationConfig.authenticationManager(authConfig);

        // Then
        assertNotNull(result);
        verify(authConfig).getAuthenticationManager();
    }

    @Test
    void passwordEncoder_shouldReturnPasswordEncoder() {
        // When
        PasswordEncoder result = applicationConfig.passwordEncoder();

        // Then
        assertNotNull(result);
    }

    @Test
    void userDetailsService_shouldThrowException_whenUserNotFound() {
        // Given
        String username = "unknown@example.com";
        when(userRepository.findByEmail(username)).thenReturn(java.util.Optional.empty());

        // When / Then
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }

    @Test
    void userDetailsService_shouldReturnUserDetails_whenUserFound() {
        // Given
        String username = "test@example.com";
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(new com.nandan.driveronboarding.entities.User()));

        // When / Then
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();
        assertNotNull(userDetailsService.loadUserByUsername(username));
    }

    private static class User {}

}
