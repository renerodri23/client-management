package com.renerodriguez.core_client_management_system.adapters.out.db.mapper;

import com.renerodriguez.core_client_management_system.adapters.out.db.entity.DireccionEntity;
import com.renerodriguez.core_client_management_system.adapters.out.db.entity.DocumentoEntity;
import com.renerodriguez.core_client_management_system.adapters.out.db.entity.UserEntity;
import com.renerodriguez.core_client_management_system.domain.Direccion;
import com.renerodriguez.core_client_management_system.domain.DocumentoIdentidad;
import com.renerodriguez.core_client_management_system.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Mapper encargado de convertir entre el modelo de dominio {@link User}
 * y las entidades de persistencia {@link UserEntity}, así como sus objetos asociados.
 *
 * <p>Se encarga de transformar objetos del dominio hacia entidades JPA para su almacenamiento,
 * y viceversa, manteniendo la separación entre la capa de dominio y la capa de infraestructura.</p>
 *
 * <p>Incluye el mapeo de:
 * <ul>
 *     <li>Direcciones ({@link Direccion} ↔ {@link DireccionEntity})</li>
 *     <li>Documentos de identidad ({@link DocumentoIdentidad} ↔ {@link DocumentoEntity})</li>
 * </ul>
 * </p>
 */
@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;

        return UserEntity.builder()
                .userId(domain.uuid())
                .nombre(domain.nombre())
                .apellido(domain.apellido())
                .email(domain.email())
                .passwordHash(domain.passwordHash())
                .direcciones(mapDireccionesToEntity(domain.direcciones()))
                .documentos(mapDocumentosToEntity(domain.documentosIdentidad()))
                .role(domain.role())
                .active(domain.active())
                .build();
    }

    public User toDomainModel(UserEntity entity) {
        if (entity == null) return null;

        return new User(
                entity.getUserId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getDirecciones().stream()
                        .map(d -> new Direccion(
                                d.getAddressId(),
                                d.getCalle(),
                                d.getCiudad(),
                                d.getDepartamento(),
                                d.getTipoDireccion()))
                        .collect(Collectors.toList()),
                entity.getDocumentos().stream()
                        .map(doc -> new DocumentoIdentidad(
                                doc.getDocId(),
                                doc.getTipo(),
                                doc.getValor()))
                        .collect(Collectors.toList()),
                entity.getRole(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy()
        );
    }

    public Set<DireccionEntity> mapDireccionesToEntity(List<Direccion> domainList) {
        if (domainList == null) return new HashSet<>();
        return domainList.stream()
                .map(d -> DireccionEntity.builder()
                        .addressId(d.uuidDir())
                        .calle(d.calle())
                        .ciudad(d.ciudad())
                        .departamento(d.departamento())
                        .tipoDireccion(d.tipo())
                        .build())
                .collect(Collectors.toSet());
    }

    public Set<DocumentoEntity> mapDocumentosToEntity(List<DocumentoIdentidad> domainList) {
        if (domainList == null) return new HashSet<>();
        return domainList.stream()
                .map(doc -> DocumentoEntity.builder()
                        .docId(doc.uuidDoc())
                        .tipo(doc.tipo())
                        .valor(doc.valor())
                        .build())
                .collect(Collectors.toSet());
    }
}