package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import java.util.UUID;
/**

 DTO de respuesta que representa un documento.
 @param uuid identificador único del documento
 @param tipo tipo del documento
 @param valor valor asociado al documento
 */
public record DocumentoResponse(
        UUID uuid,
        String tipo,
        String valor
) {
}
