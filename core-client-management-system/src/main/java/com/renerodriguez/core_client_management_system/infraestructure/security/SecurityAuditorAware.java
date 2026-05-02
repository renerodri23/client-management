package com.renerodriguez.core_client_management_system.infraestructure.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
/**
 * Componente encargado de proporcionar la identidad del usuario actual para la auditoría automática de entidades.
 * <p>
 * Implementa {@link AuditorAware} para integrarse con Spring Data JPA, permitiendo que campos como
 * {@code createdBy} y {@code updatedBy} se completen automáticamente basándose en el contexto
 * de seguridad de Spring Security.
 * </p>
 * * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Recupera el nombre de usuario del sujeto autenticado actualmente en el sistema.
 * <p>
 * El proceso de determinación del auditor sigue esta lógica:
 * <ul>
 * <li>Si no hay autenticación, el usuario no está autenticado o es un usuario anónimo,
 * se retorna "SYSTEM" como auditor por defecto.</li>
 * <li>En caso contrario, se retorna el nombre (username) extraído del objeto de autenticación.</li>
 * </ul>
 * </p>
 * * @return Un {@link Optional} que contiene el nombre del auditor actual o "SYSTEM".
 */
@Component("auditorProvider")
public class SecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.of("SYSTEM");
        }


        return Optional.of(authentication.getName());
    }
}