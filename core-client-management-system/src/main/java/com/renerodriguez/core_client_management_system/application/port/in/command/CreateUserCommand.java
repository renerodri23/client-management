package com.renerodriguez.core_client_management_system.application.port.in.command;

import java.util.List;

/**
 * Comando para crear un nuevo usuario en el sistema de gestión de clientes.
 * <p>
 * Este comando encapsula toda la información necesaria para registrar un usuario,
 * incluyendo datos personales, credenciales de acceso, direcciones, documentos y rol asignado.
 *
 * @param nombre        el nombre del usuario
 * @param apellido      el apellido del usuario
 * @param email         el correo electrónico del usuario
 * @param password      la contraseña del usuario
 * @param direcciones   la lista de direcciones asociadas al usuario
 * @param documentos    la lista de documentos asociados al usuario
 * @param role          el rol asignado al usuario dentro del sistema
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
