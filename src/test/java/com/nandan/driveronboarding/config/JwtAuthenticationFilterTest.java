package com.nandan.driveronboarding.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nandan.driveronboarding.entities.Token;
import com.nandan.driveronboarding.entities.User;
import com.nandan.driveronboarding.entities.UserContextHolder;
import com.nandan.driveronboarding.service.JwtService;
import com.nandan.driveronboarding.repository.TokenRepository;
import com.nandan.driveronboarding.util.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.OncePerRequestFilter;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {

    private MockMvc mockMvc;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup().addFilter(jwtAuthenticationFilter).build();
    }

    @Test
    public void shouldNotAuthenticateWithInvalidToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader(Constant.AUTHORIZATION)).thenReturn("Bearer Token");
        when(jwtService.extractUsername("Token")).thenReturn("user@example.com");
        when(tokenRepository.findByToken("Token")).thenReturn(Optional.empty());

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(tokenRepository).findByToken("Token");
        verify(userDetailsService).loadUserByUsername("user@example.com");
    }

    @Test
    public void shouldNotAuthenticateWithExpiredToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader(Constant.AUTHORIZATION)).thenReturn("Bearer Token");
        when(jwtService.extractUsername("Token")).thenReturn("user@example.com");
        when(tokenRepository.findByToken("Token")).thenReturn(Optional.empty());

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(tokenRepository).findByToken("Token");
        verify(userDetailsService).loadUserByUsername("user@example.com");
    }
}
