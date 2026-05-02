package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
/**

 DTO de solicitud para la creación de un usuario.
 @param nombre nombre del usuario; no puede estar vacío
 @param apellido apellido del usuario; no puede estar vacío
 @param email correo electrónico del usuario; debe ser válido y no puede estar vacío
 @param password contraseña del usuario; debe tener al menos 8 caracteres y no puede estar vacía
 @param direcciones lista de direcciones asociadas al usuario
 @param documentos lista de documentos asociados al usuario
 @param role rol asignado al usuario; no puede estar vacío
 */
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
