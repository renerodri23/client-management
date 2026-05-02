package com.renerodriguez.core_client_management_system.application.port.in.command;

import java.util.List;
import java.util.UUID;
/**
 * Objeto de comando destinado a la actualización de los datos de un usuario existente.
 * <p>
 * Este record actúa como un Data Transfer Object (DTO) inmutable que transporta los cambios
 * desde la capa de entrada (API/Web) hacia los casos de uso del dominio. Permite la
 * modificación parcial o total de los atributos del usuario y sus colecciones asociadas.
 * </p>
 *
 * @param userId      Identificador único del usuario que se desea modificar.
 * @param nombre      Nuevo nombre del usuario (opcional).
 * @param apellido    Nuevo apellido del usuario (opcional).
 * @param email       Nueva dirección de correo electrónico (opcional, debe ser validada).
 * @param direcciones Lista actualizada de comandos de dirección {@link DireccionesCommand}.
 * @param documentos  Lista actualizada de comandos de documentos {@link DocumentoCommand}.
 * @param role        Nuevo rol asignado al usuario en formato String.
 */
public record UpdateUserCommand(
        UUID userId,
        String nombre,
        String apellido,
        String email,
        List<DireccionesCommand> direcciones,
        List<DocumentoCommand> documentos,
        String role
) {
}
