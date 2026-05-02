package com.renerodriguez.core_client_management_system.application.port.in.command;

import java.util.UUID;
/**
 * Objeto de comando diseñado para la eliminación de un documento de identidad específico.
 * <p>
 * Este record encapsula la relación necesaria para identificar de forma unívoca
 * qué documento debe ser removido de la colección de un usuario determinado,
 * facilitando la trazabilidad de la operación en la capa de aplicación.
 * </p>
 *
 * @param userUuid     Identificador único del usuario propietario del documento.
 * @param documentUuid Identificador único del documento que se desea eliminar.
 */
public record DeleteDocumentCommand(UUID userUuid, UUID documentUuid) {}
