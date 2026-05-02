package com.renerodriguez.core_client_management_system.application.port.out;

import com.renerodriguez.core_client_management_system.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Puerto de salida que define el contrato para la persistencia y recuperación de usuarios.
 * <p>
 * Siguiendo la arquitectura hexagonal, esta interfaz abstrae la lógica de acceso a datos
 * del dominio, permitiendo que la capa de aplicación interactúe con el almacenamiento
 * sin depender de tecnologías específicas (como JPA, MongoDB o en memoria).
 * </p>
 * * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Persiste un usuario en el sistema de almacenamiento.
 * <p>
 * Si el usuario ya posee un identificador, se actualiza el registro existente;
 * de lo contrario, se crea uno nuevo.
 * </p>
 * * @param user Entidad de dominio {@link User} a guardar.
 * @return El usuario persistido, incluyendo cualquier dato generado por el sistema.
 */

/**
 * Recupera un usuario basándose en su identificador único universal (UUID).
 * * @param userId Identificador único del usuario.
 * @return Un {@link Optional} que contiene al usuario si se encuentra, o vacío si no.
 */

/**
 * Recupera un usuario utilizando su dirección de correo electrónico.
 * * @param email Correo electrónico asociado a la cuenta.
 * @return Un {@link Optional} con el usuario correspondiente.
 */

/**
 * Obtiene la colección completa de usuarios almacenados en el sistema.
 * * @return Una lista de objetos {@link User}.
 */

/**
 * Determina si existe un registro de usuario con el correo electrónico especificado.
 * * @param email Correo electrónico a verificar.
 * @return {@code true} si el email ya está en uso, {@code false} en caso contrario.
 */

/**
 * Determina si existe un registro de usuario con el identificador UUID especificado.
 * * @param userId UUID a verificar.
 * * @return {@code true} si el ID existe en el sistema.
 */
public interface UserRepository {

    User save(User user);


    Optional<User> findById(UUID userId);

    Optional<User> findByEmail(String email);


    List<User> findAll();



    boolean existsByEmail(String email);
    boolean existsById(UUID userId);
}
