package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;
/**

 DTO de solicitud para autenticación de usuario.
 @param email correo electrónico del usuario
 @param password contraseña del usuario
 */
public record AuthRequest(
        String email,
        String password
) {}

