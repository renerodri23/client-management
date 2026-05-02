package com.renerodriguez.core_client_management_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
/**
 * Clase de configuración dedicada para la persistencia de datos mediante JPA.
 * <p>
 * Su propósito principal es activar y configurar la auditoría de entidades de Spring Data,
 * permitiendo el rastreo automático de quién crea o modifica los registros en la base de datos.
 * </p>
 * * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Habilita la auditoría de JPA en la aplicación.
 * <p>
 * El atributo {@code auditorAwareRef} apunta al bean {@code auditorProvider},
 * el cual debe estar implementado para resolver la identidad del usuario actual
 * (usualmente integrado con el contexto de seguridad).
 * </p>
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {
}