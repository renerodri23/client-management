package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDocumentRequest(
        @NotNull TipoDocumentoIdentidad tipo,
        @NotBlank String valor
) {}
