package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import com.renerodriguez.core_client_management_system.domain.enums.RolesUser;

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
 * @param userId identificador único del usuario
 * @param nombre nombre del usuario
 * @param apellido apellido del usuario
 * @param email correo electrónico del usuario
 * @param direcciones lista de direcciones asociadas al usuario
 * @param documentos lista de documentos de identidad asociados al usuario
 * @param role rol del usuario en el sistema
 * @param active indica si el usuario está activo
 */
public record UserResponse(
        UUID userId,
        String nombre,
        String apellido,
        String email,
        List<DireccionResponse>direcciones,
        List<DocumentoResponse> documentos,
        RolesUser role,
        boolean active
) {
}
