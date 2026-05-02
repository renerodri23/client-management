package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
/**

 DTO de solicitud para la creación o actualización de un documento.
 @param tipo tipo del documento; no puede estar vacío
 @param valor valor del documento; no puede estar vacío
 */
public record DocumentoRequest(
        @NotBlank(message = "El número de documento no puede estar vacío")
        String tipo,
        @NotBlank(message = "El valor del documento no puede estar vacío")
        String valor
) {
}
