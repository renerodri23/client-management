package com.renerodriguez.core_client_management_system.application.port.in.command;

import java.util.List;
import java.util.UUID;
/**
 * Comando para actualizar la información de un usuario existente en el sistema de gestión de clientes.
 * Contiene el identificador del usuario a actualizar y los nuevos datos que se desean modificar, incluyendo su nombre, apellido, email, contraseña y dirección.
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
