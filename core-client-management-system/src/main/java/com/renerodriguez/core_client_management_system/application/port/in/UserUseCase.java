package com.renerodriguez.core_client_management_system.application.port.in;

import com.renerodriguez.core_client_management_system.application.port.in.command.CreateUserCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.UpdateUserCommand;
import com.renerodriguez.core_client_management_system.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de entrada que centraliza los casos de uso principales para la gestión de usuarios.
 * <p>
 * Esta interfaz define el contrato para las operaciones de ciclo de vida del usuario,
 * incluyendo la persistencia, búsqueda, cambios de estado y generación de reportes
 * administrativos dentro del sistema.
 * </p>
 *
 * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Orquestas el proceso de creación de un nuevo usuario en el sistema.
 *
 * @param command Objeto de comando que contiene los datos validados para el registro.
 * @return El {@link User} creado con su identidad persistida.
 */

/**
 * Gestiona la actualización de la información de un usuario existente.
 *
 * @param command Objeto de comando con los datos a modificar y el identificador del usuario.
 * @return El {@link User} tras aplicar los cambios en el dominio.
 */

/**
 * Realiza la desactivación o borrado lógico de un usuario.
 *
 * @param id Identificador único (UUID) del usuario a eliminar.
 */

/**
 * Reactiva un usuario previamente desactivado en el sistema.
 *
 * @param id Identificador único (UUID) del usuario a habilitar.
 */

/**
 * Busca un usuario por su identificador único.
 *
 * @param id UUID del usuario.
 * @return Un {@link Optional} con el usuario si existe, de lo contrario vacío.
 */

/**
 * Localiza a un usuario mediante su dirección de correo electrónico.
 *
 * @param email Correo electrónico registrado.
 * @return Un {@link Optional} con el usuario correspondiente.
 */

/**
 * Recupera la totalidad de usuarios registrados en el sistema.
 *
 * @return Una lista de objetos {@link User}.
 */

/**
 * Valida si una dirección de correo electrónico ya se encuentra registrada.
 *
 * @param email Correo a verificar.
 * @return {@code true} si el email ya existe.
 */

/**
 * Genera un archivo binario que representa un reporte detallado de los usuarios.
 * <p>
 * Comúnmente utilizado para exportar datos en formatos como CSV o Excel.
 * </p>
 *
 * @return Un arreglo de bytes (byte array) con el contenido del reporte.
 */
public interface UserUseCase {


    User createUser(CreateUserCommand command);


    User updateUser(UpdateUserCommand command);

    void deleteUser(UUID id);

    void activateUser(UUID id);

    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    boolean existsByEmail(String email);

    byte[] generateUserReport();
}
