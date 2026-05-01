package com.renerodriguez.core_client_management_system.application.port.out;

import com.renerodriguez.core_client_management_system.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
   /**
     * Guarda un nuevo usuario en el sistema.
     */
    User save(User user);

    /**
     * Busca un usuario por su ID.
     */
    Optional<User> findById(UUID userId);

    /**
     * Busca un usuario por su email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Busca todos los usuarios registrados en el sistema.
     */
    List<User> findAll();


    /**
     * Verifica si un usuario con el email o id dado ya existe en el sistema.
     */
    boolean existsByEmail(String email);
    boolean existsById(UUID userId);
}
