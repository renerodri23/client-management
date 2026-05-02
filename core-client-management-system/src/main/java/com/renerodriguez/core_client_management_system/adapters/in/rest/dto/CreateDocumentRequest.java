package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/**

 DTO de solicitud para la creación de un documento de identidad.
 @param tipo tipo de documento de identidad; no puede ser nulo
 @param valor valor del documento; no puede estar vacío
 */
public record CreateDocumentRequest(
        @NotNull TipoDocumentoIdentidad tipo,
        @NotBlank String valor
) {}
