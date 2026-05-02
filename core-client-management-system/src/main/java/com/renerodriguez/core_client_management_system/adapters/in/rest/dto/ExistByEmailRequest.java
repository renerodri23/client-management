package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
/**
 * DTO de solicitud utilizado para verificar la existencia de un usuario por su correo electrónico.
 *
 * <p>Este request se emplea en operaciones de validación donde se necesita comprobar
 * si un email ya está registrado en el sistema.</p>
 *
 * <p>Incluye validaciones de Bean Validation para asegurar que el correo tenga un formato válido
 * y no esté vacío.</p>
 *
 * @param email correo electrónico a verificar
 */
public record ExistByEmailRequest(
        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Email(message = "Debe ser un correo electrónico válido")
        String email
) {
}
