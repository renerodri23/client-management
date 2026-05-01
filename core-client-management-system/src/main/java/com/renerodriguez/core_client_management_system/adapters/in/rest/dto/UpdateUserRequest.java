package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdateUserRequest(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,
        @NotBlank(message = "El apellido no puede estar vacío")
        String apellido,
        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Email(message="Debe ser un correo electrónico válido")
        String email,

        List<DireccionRequest> direcciones,
        List<DocumentoRequest> documentos,

        @NotBlank(message = "El role no puede estar vacío")
        String role
){



}
