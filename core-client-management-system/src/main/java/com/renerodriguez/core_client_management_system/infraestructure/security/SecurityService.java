package com.renerodriguez.core_client_management_system.infraestructure.security;

import com.renerodriguez.core_client_management_system.application.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;
/**
 * Servicio de seguridad para la evaluación de permisos dinámicos y autorizaciones personalizadas.
 * <p>
 * Se utiliza principalmente en anotaciones de seguridad como {@code @PreAuthorize} para
 * verificar reglas de negocio que dependen del contexto de autenticación y la propiedad de los datos.
 * </p>
 * * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Verifica si el usuario autenticado actualmente es el propietario del recurso identificado por el UUID proporcionado.
 * <p>
 * Este método compara el correo electrónico extraído del contexto de seguridad con el
 * correo electrónico registrado en el dominio del usuario solicitado.
 * </p>
 * * @param authentication Objeto que contiene los detalles del usuario autenticado (Principal).
 * *@param "userUuid"       Identificador único del usuario al que se intenta acceder o modificar.
 * *@return {@code true} si el email autenticado coincide con el del recurso, {@code false} en caso contrario
 * o si el usuario no existe.
 */

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;

    public boolean isOwner(Authentication authentication, UUID userUuid) {
        String loggedInEmail = authentication.getName();
        return userRepository.findById(userUuid)
                .map(user -> user.email().equalsIgnoreCase(loggedInEmail))
                .orElse(false);
    }
}