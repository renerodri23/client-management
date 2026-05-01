package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import java.util.UUID;

public record DocumentoResponse(
        UUID uuid,
        String tipo,
        String valor
) {
}
