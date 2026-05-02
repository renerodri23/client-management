package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import java.util.UUID;
/**

 DTO de respuesta que representa una dirección.
 @param uuid identificador único de la dirección
 @param calle calle de la dirección
 @param ciudad ciudad de la dirección
 @param departamento departamento de la dirección
 @param tipo tipo de dirección
 */
public record DireccionResponse(
        UUID uuid,
        String calle,
        String ciudad,
        String departamento,
        String tipo
) {
}
