package com.renerodriguez.core_client_management_system.application.service;

import com.renerodriguez.core_client_management_system.application.port.in.UserUpdateUseCase;
import com.renerodriguez.core_client_management_system.application.port.in.command.AddAddressCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.AddDocumentCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.DeleteAddressCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.DeleteDocumentCommand;
import com.renerodriguez.core_client_management_system.application.port.out.UserRepository;
import com.renerodriguez.core_client_management_system.domain.Direccion;
import com.renerodriguez.core_client_management_system.domain.DocumentoIdentidad;
import com.renerodriguez.core_client_management_system.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Servicio de aplicación que implementa el caso de uso para la actualización específica de componentes de un usuario.
 * <p>
 * Esta clase se especializa en la gestión granular de colecciones asociadas al usuario,
 * como direcciones y documentos, siguiendo los principios de inmutabilidad del dominio
 * al reconstruir el objeto {@link User} cuando es necesario.
 * </p>
 *
 * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Elimina una dirección específica del perfil de un usuario.
 * <p>
 * Busca al usuario por su identificador y remueve la dirección cuya UUID coincida con la
 * proporcionada en el comando. La operación se realiza de forma transaccional.
 * </p>
 *
 * @param command Objeto que contiene el UUID del usuario y el UUID de la dirección a eliminar.
 * @throws RuntimeException si el usuario no existe en la persistencia.
 */

/**
 * Elimina un documento de identidad específico del perfil de un usuario.
 * <p>
 * Localiza al usuario y filtra la lista de documentos para remover aquel que coincida
 * con el identificador técnico proporcionado.
 * </p>
 *
 * @param command Objeto que contiene el UUID del usuario y el UUID del documento a eliminar.
 * @throws RuntimeException si el usuario no es encontrado.
 */

/**
 * Agrega una nueva dirección a la colección existente del usuario.
 * <p>
 * Crea una nueva instancia de {@link Direccion} con un UUID aleatorio y reconstruye
 * el registro del usuario incorporando la nueva lista para persistir el cambio.
 * </p>
 *
 * @param command Datos de la nueva dirección y referencia del usuario.
 */

/**
 * Adjunta un nuevo documento de identidad al usuario.
 * <p>
 * Genera una entidad de dominio {@link DocumentoIdentidad} inmutable y actualiza el
 * agregado del usuario mediante la reconstrucción del record.
 * </p>
 *
 * @param command Datos del documento y referencia del usuario.
 */

/**
 * Método de utilidad para reconstruir el objeto User con una lista de direcciones actualizada.
 * <p>
 * Mantiene la integridad de los campos de auditoría y otros atributos inmutables
 * mientras inyecta la nueva colección de direcciones.
 * </p>
 *
 * @param u  Instancia original del usuario.
 * @param ad Nueva lista de direcciones.
 * @return Una nueva instancia de {@link User} con las direcciones actualizadas.
 */

/**
 * Método de utilidad para reconstruir el objeto User con una lista de documentos actualizada.
 * <p>
 * Preserva el estado actual del usuario y sustituye únicamente la colección de
 * documentos de identidad.
 * </p>
 *
 * * @param u    Instancia original del usuario.
 * * @param docs Nueva lista de documentos de identidad.
 * @return Una nueva instancia de {@link User} con los documentos actualizados.
 */
@Service
@RequiredArgsConstructor
public class UserUpdateService implements UserUpdateUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void deleteAddress(DeleteAddressCommand command) {
        var user = userRepository.findById(command.userUuid())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.direcciones().removeIf(d -> d.uuidDir().equals(command.addressUuid()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteDocument(DeleteDocumentCommand command) {
        var user = userRepository.findById(command.userUuid())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.documentosIdentidad().removeIf(doc -> doc.uuidDoc().equals(command.documentUuid()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void addAddress(AddAddressCommand command) {
        var user = userRepository.findById(command.userUuid())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Direccion nuevaDir = new Direccion(
                UUID.randomUUID(),
                command.calle(),
                command.ciudad(),
                command.departamento(),
                command.tipo()
        );

        List<Direccion> nuevasDirecciones = new ArrayList<>(user.direcciones());
        nuevasDirecciones.add(nuevaDir);

        User userActualizado = rebuildUserWithAddresses(user, nuevasDirecciones);
        userRepository.save(userActualizado);
    }

    @Override
    @Transactional
    public void addDocument(AddDocumentCommand command) {
        var user = userRepository.findById(command.userUuid())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DocumentoIdentidad nuevoDoc = new DocumentoIdentidad(
                UUID.randomUUID(),
                command.tipo(),
                command.valor()
        );

        List<DocumentoIdentidad> nuevosDocs = new ArrayList<>(user.documentosIdentidad());
        nuevosDocs.add(nuevoDoc);

        User userActualizado = rebuildUserWithDocuments(user, nuevosDocs);
        userRepository.save(userActualizado);
    }

    private User rebuildUserWithAddresses(User u, List<Direccion> ad) {
        return new User(u.uuid(), u.nombre(), u.apellido(), u.email(), u.passwordHash(), ad, u.documentosIdentidad(), u.role(), u.active(), u.createdAt(),u.updatedAt(), u.createdBy(), u.updatedBy());
    }

    private User rebuildUserWithDocuments(User u, List<DocumentoIdentidad> docs) {
        return new User(u.uuid(), u.nombre(), u.apellido(), u.email(), u.passwordHash(), u.direcciones(), docs, u.role(), u.active(), u.createdAt(),u.updatedAt(), u.createdBy(), u.updatedBy());
    }
}