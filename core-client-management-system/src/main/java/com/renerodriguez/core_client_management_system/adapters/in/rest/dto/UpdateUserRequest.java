package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;
/**
 * DTO de respuesta que representa la información de un usuario expuesta por la API REST.
 *
 * <p>Este objeto se utiliza para devolver datos del usuario al cliente, incluyendo
 * información básica, direcciones asociadas, documentos de identidad, rol y estado.</p>
 *
 * <p>No contiene información sensible como contraseñas ni datos internos del sistema.</p>
 *
 * * @param userId identificador único del usuario
 * @param nombre nombre del usuario
 * @param apellido apellido del usuario
 * @param email correo electrónico del usuario
 * @param direcciones lista de direcciones asociadas al usuario
 * @param documentos lista de documentos de identidad asociados al usuario
 * @param role rol del usuario en el sistema
 * * @param active indica si el usuario está activo
 */
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
