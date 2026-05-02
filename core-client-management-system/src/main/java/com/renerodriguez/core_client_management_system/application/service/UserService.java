package com.renerodriguez.core_client_management_system.application.service;

import com.renerodriguez.core_client_management_system.application.port.in.UserUseCase;
import com.renerodriguez.core_client_management_system.application.port.in.command.CreateUserCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.DireccionesCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.DocumentoCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.UpdateUserCommand;
import com.renerodriguez.core_client_management_system.application.port.out.UserRepository;
import com.renerodriguez.core_client_management_system.domain.Direccion;
import com.renerodriguez.core_client_management_system.domain.DocumentoIdentidad;
import com.renerodriguez.core_client_management_system.domain.User;
import com.renerodriguez.core_client_management_system.domain.enums.RolesUser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * Implementación del servicio de aplicación para la gestión integral de usuarios.
 * <p>
 * Esta clase orquestra la lógica de negocio, validaciones de dominio, persistencia
 * y generación de reportes. Implementa el puerto de entrada {@link UserUseCase}
 * y asegura la consistencia de los datos mediante el uso de transacciones.
 * </p>
 *
 * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Crea un nuevo usuario en el sistema.
 * <p>
 * Realiza validaciones exhaustivas (campos obligatorios, formato de email,
 * existencia previa) y transforma los comandos de entrada en entidades de dominio.
 * La contraseña se almacena cifrada mediante BCrypt.
 * </p>
 *
 * @param command Objeto con los datos necesarios para la creación del usuario.
 * @return El usuario creado y persistido con sus identificadores generados.
 * @throws IllegalArgumentException si los datos de entrada son inválidos o el email ya existe.
 */

/**
 * Actualiza la información de un usuario existente.
 * <p>
 * Permite la actualización parcial de los datos. Si no se envían listas de direcciones
 * o documentos, se preservan los valores actuales del registro.
 * </p>
 *
 * @param command Objeto que contiene el ID del usuario y los campos a modificar.
 * @return El usuario con la información actualizada.
 * @throws IllegalArgumentException si el usuario no existe o el nuevo email está en uso.
 */

/**
 * Realiza un borrado lógico del usuario en el sistema.
 * <p>
 * En lugar de eliminar el registro físicamente, cambia el estado {@code active} a false.
 * </p>
 *
 * @param id Identificador único del usuario a desactivar.
 */

/**
 * Reactiva un usuario previamente desactivado.
 *
 * @param id Identificador único del usuario a activar.
 */

/**
 * Busca un usuario por su identificador único.
 *
 * @param id UUID del usuario.
 * @return Un {@link Optional} con el usuario encontrado o vacío si no existe.
 */

/**
 * Busca un usuario por su dirección de correo electrónico.
 *
 * @param email Correo electrónico a buscar.
 * @return Un {@link Optional} con el usuario correspondiente.
 */

/**
 * Recupera la lista completa de usuarios registrados.
 *
 * @return Lista de todos los usuarios en el sistema.
 */

/**
 * Verifica la existencia de un email en la base de datos.
 *
 * @param email Correo electrónico a verificar.
 * @return {@code true} si el email ya está registrado.
 */

/**
 * Genera un reporte consolidado de usuarios en formato CSV.
 * <p>
 * El reporte incluye información personal, estado, documentos de identidad,
 * direcciones y metadatos de auditoría.
 * </p>
 *
 * @return Un arreglo de bytes que representa el archivo CSV generado.
 */

/**
 * Convierte una lista de comandos de dirección al modelo de dominio.
 *
 * @param commands Lista de comandos de entrada.
 * @return Lista de entidades de dominio {@link Direccion}.
 */

/**
 * Convierte una lista de comandos de documentos al modelo de dominio.
 *
 * * @param commands Lista de comandos de entrada.
 * *@return Lista de entidades de dominio {@link DocumentoIdentidad}.
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService implements UserUseCase {
    public final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    /**
     * Crea un nuevo usuario en el sistema, validando los datos de entrada y asegurando que el email no esté registrado previamente.
     * También se encarga de mapear las direcciones y documentos de identidad desde los comandos a las entidades de dominio.
     */
    @Override
    public User createUser(CreateUserCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()){
            log.info("[UserService] El nombre es obligatorio");
            throw new IllegalArgumentException("El nombre es obligatorio");}

        if (command.apellido() == null || command.apellido().isBlank()){
            log.info("[UserService] El apellido es obligatorio");
            throw new IllegalArgumentException("El apellido es obligatorio");}

        if (command.email() == null || command.email().isBlank()){
            log.info("[UserService] El email es obligatorio");
            throw new IllegalArgumentException("El email es obligatorio");}

        if (command.password() == null || command.password().isBlank()){
            log.info("[UserService] La contraseña es obligatoria");
            throw new IllegalArgumentException("La contraseña es obligatoria");}

        if (!command.email().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            log.info("[UserService] El email no es válido");
            throw new IllegalArgumentException("El email no es válido");}

        if (command.direcciones() == null || command.direcciones().isEmpty()){
            log.info("[UserService] Al menos una dirección es obligatoria");
            throw new IllegalArgumentException("Al menos una dirección es obligatoria");}

        command.direcciones().forEach(dir -> {
            if(dir.calle().isBlank() || dir.ciudad().isBlank()){
                log.info("[UserService] La calle es obligatoria en la dirección");
                throw new IllegalArgumentException("La calle es obligatoria en la dirección");}
        });

        if (command.documentos() == null || command.documentos().isEmpty()){
            log.info("[UserService] Al menos un documento es obligatorio");
            throw new IllegalArgumentException("Al menos un documento es obligatorio");}

        command.documentos().forEach(doc -> {
            if (doc.valor().isBlank()){
                log.info("[UserService] El valor es obligatorio en el documento");
                throw new IllegalArgumentException("El valor es obligatorio en el documento");}
        });

        if (command.role() == null || command.role().isBlank()) {
            log.info("[UserService] El role es obligatorio");
            throw new IllegalArgumentException("El role es obligatorio (ADMIN o USER)");
        }

        if (userRepository.existsByEmail(command.email().toLowerCase())) {
            log.info("[UserService] El email ya está registrado: {}", command.email());
            throw new IllegalArgumentException("El email ya está registrado");
        }
        String hashedPassword = passwordEncoder.encode(command.password());

        List<Direccion> direccionesDomain = mapDireccionesToDomain(command.direcciones());
        List<DocumentoIdentidad> documentosDomain = mapDocumentosToDomain(command.documentos());

        User newUser = new User(
                null,
                command.nombre(),
                command.apellido(),
                command.email().toLowerCase(),
                hashedPassword,
                direccionesDomain,
                documentosDomain,
                RolesUser.valueOf(command.role().toUpperCase()),
                true,
                null,
                null,
                null,
                null

        );

        return userRepository.save(newUser);

    }


    /**
     * Actualiza la información de un usuario existente en el sistema, validando los datos de entrada y asegurando que el email no esté registrado por otro usuario.
     * También se encarga de mapear las direcciones y documentos de identidad desde los comandos a las entidades de dominio, manteniendo las existentes si no se proporcionan nuevas.
     */
    @Override
    public User updateUser(UpdateUserCommand command) {
        User u = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (command.email() != null && !command.email().equalsIgnoreCase(u.email())){
            if(userRepository.existsByEmail(command.email().toLowerCase())){
                log.info("[UserService] El email ya está registrados: {}", command.email());
                throw new IllegalArgumentException("El email ya está registrado");
            }
        }

        List<Direccion> updatedDirecciones = command.direcciones() != null
                ? mapDireccionesToDomain(command.direcciones())
                : u.direcciones();

        List<DocumentoIdentidad> updatedDocumentos = command.documentos() != null
                ? mapDocumentosToDomain(command.documentos())
                : u.documentosIdentidad();

        RolesUser nuevoRol = command.role() != null
                ? RolesUser.valueOf(command.role().toUpperCase())
                : u.role();

        User updatedUser = new User(
                u.uuid(),
                command.nombre() != null ? command.nombre() : u.nombre(),
                command.apellido() != null ? command.apellido() : u.apellido(),
                command.email() != null ? command.email().toLowerCase() : u.email(),
                u.passwordHash(),
                updatedDirecciones,
                updatedDocumentos,
                nuevoRol,
                u.active(),
                null,
                null,
                null,
                null
        );

        log.info("[UserService] Updated user: {}", updatedUser);
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        User deactivatedUser = new User(
                u.uuid(),
                u.nombre(),
                u.apellido(),
                u.email(),
                u.passwordHash(),
                u.direcciones(),
                u.documentosIdentidad(),
                u.role(),
                false,
                null,
                null,
                null,
                null
        );

        userRepository.save(deactivatedUser);
        log.info("[UserService] Usuario desactivado (borrado lógico) con ID: {}", id);
    }
    @Override
    public void activateUser(UUID id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        User activatedUser = new User(
                u.uuid(),
                u.nombre(),
                u.apellido(),
                u.email(),
                u.passwordHash(),
                u.direcciones(),
                u.documentosIdentidad(),
                u.role(),
                true,
                null,
                null,
                null,
                null
        );

        userRepository.save(activatedUser);
        log.info("[UserService] Usuario reactivado con ID: {}", id);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public byte[] generateUserReport() {
        List<User> users = userRepository.findAll();
        StringBuilder sb = new StringBuilder();

        sb.append("UUID,Nombre,Apellido,Email,Estado,Documentos,Direcciones,Creado_Por,Fecha_Creacion\n");

        for(User u : users) {
            String docs = u.documentosIdentidad().isEmpty() ? "N/A" :
                    u.documentosIdentidad().stream()
                    .map(d -> d.tipo() + ":" + d.valor())
                    .collect(Collectors.joining(" | "));

            String dirs = u.direcciones().isEmpty() ? "N/A" :
                    u.direcciones().stream()
                    .map(d -> d.tipo() + ": " + d.calle() + " (" + d.ciudad() + ")")
                    .collect(Collectors.joining(" | "));

            sb.append(u.uuid()).append(",")
                    .append(u.nombre()).append(",")
                    .append(u.apellido()).append(",")
                    .append(u.email()).append(",")
                    .append(u.active() ? "ACTIVO" : "INACTIVO").append(",")
                    .append("\"").append(docs).append("\",")
                    .append("\"").append(dirs).append("\",")
                    .append(u.createdBy() != null ? u.createdBy() : "SYSTEM").append(",")
                    .append(u.createdAt() != null ? u.createdAt().toString() : "N/A").append("\n");
        }

        return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }


    private List<Direccion> mapDireccionesToDomain(List<DireccionesCommand> commands) {
        if (commands == null) return List.of();
        return commands.stream()
                .map(d -> new Direccion(
                        null,
                        d.calle(),
                        d.ciudad(),
                        d.departamento(),
                        d.tipoDireccion()
                ))
                .toList();
    }

    private List<DocumentoIdentidad> mapDocumentosToDomain(List<DocumentoCommand> commands) {
        if (commands == null) return List.of();
        return commands.stream()
                .map(doc -> new DocumentoIdentidad(
                        null,
                        doc.tipo(),
                        doc.valor()
                ))
                .toList();
    }
}
