package com.renerodriguez.core_client_management_system.application.port.in.command;
/**
 * Comando para buscar usuarios en el sistema de gestión de clientes.
 * Permite filtrar por nombre, apellido y email para encontrar usuarios específicos.
 */
public record SearchUserCommand(
        String nombre,
        String apellido,
        String email
) {
}
