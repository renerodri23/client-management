package com.renerodriguez.core_client_management_system.adapters.out.db.entity;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
/**
 * Entidad JPA que representa la tabla "user_addresses" en la base de datos.
 *
 * <p>Modela las direcciones asociadas a un usuario del sistema.</p>
 *
 * <p>Cada dirección pertenece a un único {@link UserEntity} mediante una relación
 * muchos-a-uno, permitiendo que un usuario tenga múltiples direcciones registradas.</p>
 *
 * <p>Campos principales:
 * <ul>
 *     <li>addressId: identificador único de la dirección</li>
 *     <li>calle: calle de la dirección</li>
 *     <li>ciudad: ciudad</li>
 *     <li>departamento: departamento o región</li>
 *     <li>tipoDireccion: tipo de dirección ({@link TipoDireccion})</li>
 * </ul>
 * </p>
 *
 * <p>La clave primaria se genera automáticamente si no se asigna antes de persistir.</p>
 */
@Entity
@Table(name="user_addresses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DireccionEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name= "id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID addressId;

    private String calle;
    private String ciudad;
    private String departamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_direccion")
    private TipoDireccion tipoDireccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity usuario;

    @PrePersist
    void onCreate(){
        if(this.addressId == null) this.addressId = UUID.randomUUID();
    }
}
