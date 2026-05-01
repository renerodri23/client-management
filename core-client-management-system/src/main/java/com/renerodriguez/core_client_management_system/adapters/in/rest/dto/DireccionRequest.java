package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record DireccionRequest(
        @NotBlank(message = "La calle no puede estar vacía")
        String calle,
        @NotBlank(message = "El departamento no puede estar vacío")
        String ciudad,
        @NotBlank(message = "El departamento no puede estar vacío")
        String departamento,
        @NotBlank(message = "El tipo de dirección es obligatorio")
        String tipo
) {
}
