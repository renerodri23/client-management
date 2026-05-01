package com.renerodriguez.core_client_management_system.domain;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;

import java.util.UUID;

/**
 * Representa una dirección asociada a un usuario en el sistema de gestión de clientes.
 * Contiene información sobre la calle, ciudad, departamento y el tipo de dirección (casa, trabajo, facturación).
 */
public record Direccion(
        UUID uuidDir,
        String calle,
        String ciudad,
        String departamento,
        TipoDireccion tipo

) {
}
