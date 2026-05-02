package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;
/**
 * DTO de respuesta utilizado para indicar la existencia de un recurso en el sistema.
 *
 * <p>Se usa típicamente en validaciones rápidas, por ejemplo para verificar
 * si un usuario, email u otro recurso ya existe sin devolver información adicional.</p>
 *
 * @param exists indica si el recurso existe (true) o no (false)
 */
public record ExistsResponse(
        boolean exists
) {
}
