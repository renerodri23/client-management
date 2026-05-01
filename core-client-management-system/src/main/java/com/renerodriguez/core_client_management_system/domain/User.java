package com.renerodriguez.core_client_management_system.domain;

import com.renerodriguez.core_client_management_system.domain.enums.RolesUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Representa un usuario en el sistema de gestión de clientes.
 * Contiene información personal, credenciales de acceso y listas de direcciones y documentos de identidad asociados al usuario.
 */
public record User(
        UUID uuid,
        String nombre,
        String apellido,
        String email,
        String passwordHash,
        List<Direccion>direcciones,
        List<DocumentoIdentidad> documentosIdentidad,
        RolesUser role,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy

) {
}
