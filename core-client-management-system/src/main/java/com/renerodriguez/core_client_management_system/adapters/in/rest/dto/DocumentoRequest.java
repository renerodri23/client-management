package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record DocumentoRequest(
        @NotBlank(message = "El número de documento no puede estar vacío")
        String tipo,
        @NotBlank(message = "El valor del documento no puede estar vacío")
        String valor
) {
}
