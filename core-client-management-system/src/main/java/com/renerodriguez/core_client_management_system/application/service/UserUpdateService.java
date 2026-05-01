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