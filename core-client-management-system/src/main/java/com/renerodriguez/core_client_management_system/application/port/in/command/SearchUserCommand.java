package com.renerodriguez.core_client_management_system.application.port.in.command;
/**
 * Objeto de comando utilizado para encapsular los criterios de búsqueda de usuarios.
 * <p>
 * Este record actúa como un contenedor de filtros opcionales que permite realizar
 * consultas dinámicas en el repositorio de usuarios, facilitando la localización
 * de registros basados en coincidencias de datos personales.
 * </p>
 *
 * @param nombre   Filtro por nombre del usuario (puede ser parcial o exacto).
 * @param apellido Filtro por apellido del usuario (puede ser parcial o exacto).
 * @param email    Filtro por dirección de correo electrónico.
 */
public record SearchUserCommand(
        String nombre,
        String apellido,
        String email
) {
}
