package com.renerodriguez.core_client_management_system.adapters.in.rest.controller;

import com.renerodriguez.core_client_management_system.adapters.in.rest.dto.AuthRequest;
import com.renerodriguez.core_client_management_system.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request){
        String token = authService.login(request.email(), request.password());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
