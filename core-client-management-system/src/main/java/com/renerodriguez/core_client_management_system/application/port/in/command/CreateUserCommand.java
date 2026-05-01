package com.renerodriguez.core_client_management_system.application.port.in.command;

import java.util.List;

/**
 * Comando para crear un nuevo usuario en el sistema de gestión de clientes.
 * Contiene toda la información necesaria para registrar un nuevo usuario, incluyendo su nombre, apellido, email, contraseña y dirección.
 */
public record CreateUserCommand(
        String nombre,
        String apellido,
        String email,
        String password,
        List<DireccionesCommand> direcciones,
        List<DocumentoCommand> documentos,
        String role
) {
}
