package com.renerodriguez.core_client_management_system.application.port.in.command;

import java.util.UUID;
/**
 * Objeto de comando diseñado para la eliminación de una dirección específica asociada a un usuario.
 * <p>
 * Este record actúa como un portador de datos inmutable que vincula al usuario con el recurso
 * geográfico que se desea remover, permitiendo que la capa de aplicación identifique
 * exactamente qué registro debe ser procesado.
 * </p>
 *
 * @param userUuid    Identificador único del usuario al que pertenece la dirección.
 * @param addressUuid Identificador único de la dirección que se pretende eliminar.
 */
public record DeleteAddressCommand(UUID userUuid, UUID addressUuid) {}