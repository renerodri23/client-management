package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
/**

 DTO de solicitud para la creación o actualización de una dirección.
 @param calle calle de la dirección; no puede estar vacía
 @param ciudad ciudad de la dirección; no puede estar vacía
 @param departamento departamento de la dirección; no puede estar vacío
 @param tipo tipo de dirección; no puede estar vacío
 */
public record DireccionRequest(
        @NotBlank(message = "La calle no puede estar vacía")
        String calle,
        @NotBlank(message = "El departamento no puede estar vacío")
        String ciudad,
        @NotBlank(message = "El departamento no puede estar vacío")
        String departamento,
        @NotBlank(message = "El tipo de dirección es obligatorio")
        String tipo
) {
}
