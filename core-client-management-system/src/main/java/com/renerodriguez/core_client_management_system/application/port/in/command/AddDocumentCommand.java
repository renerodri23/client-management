package com.renerodriguez.core_client_management_system.application.port.in.command;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad;

import java.util.UUID;
/**
 * Comando para agregar un documento de identidad a un usuario existente.
 * <p>
 * Este comando contiene la información necesaria para asociar un documento
 * a un usuario dentro del sistema.
 *
 * @param userUuid el identificador único del usuario
 * @param tipo     el tipo de documento de identidad
 * @param valor    el valor o número del documento
 */
public record AddDocumentCommand(
        UUID userUuid,
        TipoDocumentoIdentidad tipo,
        String valor
) {}
