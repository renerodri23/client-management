package com.renerodriguez.core_client_management_system.adapters.out.db.entity;

import com.renerodriguez.core_client_management_system.domain.enums.RolesUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;
/**
 * Entidad JPA que representa la tabla "users" en la base de datos.
 *
 * <p>Contiene la información principal de un usuario del sistema, incluyendo datos personales,
 * credenciales, relaciones con direcciones y documentos, así como metadatos de auditoría.</p>
 *
 * <p>Esta entidad es gestionada por JPA/Hibernate y soporta auditoría automática mediante
 * {@link org.springframework.data.jpa.domain.support.AuditingEntityListener}.</p>
 *
 * <p>Relaciones:
 * <ul>
 *     <li>Un usuario puede tener múltiples {@link DireccionEntity}</li>
 *     <li>Un usuario puede tener múltiples {@link DocumentoEntity}</li>
 * </ul>
 * </p>
 *
 * <p>Auditoría:
 * <ul>
 *     <li>createdAt: fecha de creación automática</li>
 *     <li>updatedAt: última modificación automática</li>
 *     <li>createdBy: usuario creador</li>
 *     <li>updatedBy: usuario que modificó</li>
 * </ul>
 * </p>
 */
@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(name= "id", columnDefinition = "BINARY(16), nullable = false")
    private UUID userId;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name="apellido", nullable = false)
    private String apellido;

    @Column(name="email", nullable = false)
    private String email;

    @EqualsAndHashCode.Include
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DireccionEntity> direcciones = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DocumentoEntity> documentos = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 20)
    private RolesUser role;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

    @PrePersist
    void onCreate(){
        if(this.userId==null) this.userId = UUID.randomUUID();
    }
}
