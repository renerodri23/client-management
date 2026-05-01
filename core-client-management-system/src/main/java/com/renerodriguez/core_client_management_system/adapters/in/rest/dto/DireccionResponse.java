package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import java.util.UUID;

public record DireccionResponse(
        UUID uuid,
        String calle,
        String ciudad,
        String departamento,
        String tipo
) {
}
