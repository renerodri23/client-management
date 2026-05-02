package com.renerodriguez.core_client_management_system.domain;

import com.renerodriguez.core_client_management_system.domain.enums.RolesUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Entidad principal de dominio que representa a un usuario dentro del sistema.
 * <p>
 * Este registro encapsula la identidad, credenciales, perfiles de contacto y metadatos
 * de auditoría de un cliente o usuario administrativo. Al ser un {@code record},
 * garantiza la inmutabilidad de la estructura de datos del usuario.
 * </p>
 *
 * @param uuid               Identificador único universal del usuario.
 * @param nombre             Nombre o nombres de pila del usuario.
 * @param apellido           Apellidos del usuario.
 * @param email              Dirección de correo electrónico (utilizada habitualmente como login).
 * @param passwordHash       Representación segura (hash) de la contraseña del usuario.
 * @param direcciones        Lista de ubicaciones físicas asociadas mediante {@link Direccion}.
 * @param documentosIdentidad Lista de documentos legales asociados mediante {@link DocumentoIdentidad}.
 * @param role               Rol asignado en el sistema definido por {@link RolesUser}.
 * @param active             Estado de habilitación del usuario en el sistema.
 * @param createdAt          Fecha y hora de creación del registro.
 * @param updatedAt          Fecha y hora de la última modificación del registro.
 * @param createdBy          Identificador o nombre del autor que creó el registro.
 * @param updatedBy          Identificador o nombre del último autor que modificó el registro.
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
