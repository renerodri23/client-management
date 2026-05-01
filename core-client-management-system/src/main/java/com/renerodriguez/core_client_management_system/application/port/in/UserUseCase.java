package com.renerodriguez.core_client_management_system.application.port.in;

import com.renerodriguez.core_client_management_system.application.port.in.command.CreateUserCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.UpdateUserCommand;
import com.renerodriguez.core_client_management_system.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz que define los casos de uso relacionados con la gestión de usuarios en el sistema de gestión de clientes.
 * Proporciona métodos para crear y actualizar usuarios, utilizando comandos específicos para cada operación.
 */
public interface UserUseCase {

    /**
     * Crea un nuevo usuario en el sistema.
     */
    User createUser(CreateUserCommand command);

    /**
     * Actualiza la información de un usuario existente en el sistema.
     */
    User updateUser(UpdateUserCommand command);

    void deleteUser(UUID id);

    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    boolean existsByEmail(String email);

    byte[] generateUserReport();
}
