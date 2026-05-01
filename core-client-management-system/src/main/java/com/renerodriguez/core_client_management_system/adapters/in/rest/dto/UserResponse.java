package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import com.renerodriguez.core_client_management_system.domain.enums.RolesUser;

import java.util.List;
import java.util.UUID;

public record UserResponse(
        UUID userId,
        String nombre,
        String apellido,
        String email,
        List<DireccionResponse>direcciones,
        List<DocumentoResponse> documentos,
        RolesUser role,
        boolean active
) {
}
