package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ExistByEmailRequest(
        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Email(message = "Debe ser un correo electrónico válido")
        String email
) {
}
