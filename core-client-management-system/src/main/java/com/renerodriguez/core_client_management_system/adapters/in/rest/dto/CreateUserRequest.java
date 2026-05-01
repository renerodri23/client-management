package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateUserRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,
        @NotBlank(message = "El apellido no puede estar vacío")
        String apellido,
        @Email(message="Debe ser un correo electrónico válido")
        @NotBlank(message = "El correo electrónico no puede estar vacío")
        String email,
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        @NotBlank(message = "La contraseña no puede estar vacía")
        String password,

        List<DireccionRequest> direcciones,
        List<DocumentoRequest> documentos,

        @NotBlank(message = "El role no puede estar vacío")
        String role


) {
}
