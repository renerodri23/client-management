package com.renerodriguez.core_client_management_system.adapters.out.db.entity;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
/**
 * Entidad JPA que representa la tabla "user_documents" en la base de datos.
 *
 * <p>Modela los documentos de identidad asociados a un usuario del sistema.</p>
 *
 * <p>Cada documento está vinculado a un único {@link UserEntity} mediante una relación
 * muchos-a-uno, permitiendo que un usuario tenga múltiples documentos.</p>
 *
 * <p>Campos principales:
 * <ul>
 *     <li>docId: identificador único del documento</li>
 *     <li>tipo: tipo de documento ({@link TipoDocumentoIdentidad})</li>
 *     <li>valor: número o valor del documento</li>
 * </ul>
 * </p>
 *
 * <p>La clave primaria se genera automáticamente si no se asigna antes de persistir.</p>
 */
@Entity
@Table(name="user_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DocumentoEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name= "id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID docId;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_documento", nullable = false)
    private TipoDocumentoIdentidad tipo;

    @Column(name="valor_documento", nullable = false)
    private String valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity usuario;

    @PrePersist
    void onCreate(){
        if(this.docId == null) this.docId = UUID.randomUUID();
    }
}