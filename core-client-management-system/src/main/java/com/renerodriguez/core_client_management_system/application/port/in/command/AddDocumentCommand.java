package com.renerodriguez.core_client_management_system.application.port.in.command;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad;

import java.util.UUID;

public record AddDocumentCommand(
        UUID userUuid,
        TipoDocumentoIdentidad tipo,
        String valor
) {}
