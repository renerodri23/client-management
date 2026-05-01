package com.renerodriguez.core_client_management_system.application.service;

import com.renerodriguez.core_client_management_system.adapters.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public String login(String email, String password) {
        log.info("[AuthService] Intento de login para email: {}", email);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        var userDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtUtils.generateToken(userDetails);
        log.info("[AuthService] Login exitoso para email: {}", email);
        return token;
    }
}