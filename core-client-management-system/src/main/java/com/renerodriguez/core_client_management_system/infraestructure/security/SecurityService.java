package com.renerodriguez.core_client_management_system.infraestructure.security;

import com.renerodriguez.core_client_management_system.application.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;

    public boolean isOwner(Authentication authentication, UUID userUuid) {
        String loggedInEmail = authentication.getName();
        return userRepository.findById(userUuid)
                .map(user -> user.email().equalsIgnoreCase(loggedInEmail))
                .orElse(false);
    }
}