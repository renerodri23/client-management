package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

public record AuthRequest(
        String email,
        String password
) {}

