package com.renerodriguez.core_client_management_system.adapters.out.db;

import com.renerodriguez.core_client_management_system.adapters.out.db.entity.UserEntity;
import com.renerodriguez.core_client_management_system.adapters.out.db.mapper.UserPersistenceMapper;
import com.renerodriguez.core_client_management_system.adapters.out.db.spring.SpringDataUserRepository;
import com.renerodriguez.core_client_management_system.application.port.out.UserRepository;
import com.renerodriguez.core_client_management_system.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public User save(User user) {
        UserEntity entity;

        if (user.uuid() != null && springDataUserRepository.existsById(user.uuid())) {
            entity = springDataUserRepository.findById(user.uuid())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            entity.setNombre(user.nombre());
            entity.setApellido(user.apellido());
            entity.setEmail(user.email());
            entity.setRole(user.role());
            entity.setActive(user.active());
        } else {
            entity = userPersistenceMapper.toEntity(user);
        }

        if (entity.getDirecciones() == null) entity.setDirecciones(new HashSet<>());
        entity.getDirecciones().clear();
        entity.getDirecciones().addAll(userPersistenceMapper.mapDireccionesToEntity(user.direcciones()));
        entity.getDirecciones().forEach(dir -> dir.setUsuario(entity));

        if (entity.getDocumentos() == null) entity.setDocumentos(new HashSet<>());
        entity.getDocumentos().clear();
        entity.getDocumentos().addAll(userPersistenceMapper.mapDocumentosToEntity(user.documentosIdentidad()));
        entity.getDocumentos().forEach(doc -> doc.setUsuario(entity));

        UserEntity saved = springDataUserRepository.save(entity);
        return userPersistenceMapper.toDomainModel(saved);
    }
    @Override
    public Optional<User> findById(UUID userId) {
        return springDataUserRepository.findById(userId)
                .map(userPersistenceMapper::toDomainModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email)
                .map(userPersistenceMapper::toDomainModel);
    }

    @Override
    public List<User> findAll() {
        return springDataUserRepository.findAll()
                .stream()
                .map(userPersistenceMapper::toDomainModel)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataUserRepository.existsByEmail(email.toLowerCase());
    }

    @Override
    public boolean existsById(UUID userId) {
        return springDataUserRepository.existsById(userId);
    }
}
