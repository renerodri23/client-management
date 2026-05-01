package com.renerodriguez.core_client_management_system.adapters.out.db.entity;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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
