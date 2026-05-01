package com.renerodriguez.core_client_management_system.application.port.in.command;

public record DocumentoCommand(
        com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad tipo,
        String valor
) {
}
