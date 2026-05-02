package com.renerodriguez.core_client_management_system.domain;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;

import java.util.UUID;

/**
 * Representa una ubicación física o postal asociada a un usuario dentro del dominio.
 * <p>
 * Este registro es inmutable y encapsula la información geográfica básica
 * y el propósito de la dirección (comercial, residencial, etc.).
 * </p>
 * * @param uuidDir      Identificador único universal de la dirección.
 * @param calle        Nombre de la vía, número de inmueble y detalles adicionales.
 * @param ciudad       Municipio o ciudad de la ubicación.
 * @param departamento Estado, provincia o región administrativa.
 * @param tipo         Categoría de la dirección (e.g., RESIDENCIAL, TRABAJO) definida por {@link TipoDireccion}.
 */
public record Direccion(
        UUID uuidDir,
        String calle,
        String ciudad,
        String departamento,
        TipoDireccion tipo

) {
}
