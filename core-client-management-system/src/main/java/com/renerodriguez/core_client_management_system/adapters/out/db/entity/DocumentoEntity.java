package com.renerodriguez.core_client_management_system.adapters.out.db.entity;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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