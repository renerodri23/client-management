package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/**


 DTO de solicitud para la creación de una dirección.



 @param calle calle de la dirección; no puede estar vacía


 @param ciudad ciudad de la dirección; no puede estar vacía


 @param departamento departamento de la dirección; no puede estar vacío


 @param tipo tipo de dirección; no puede ser nulo
 */
public record CreateAddressRequest(
        @NotBlank String calle,
        @NotBlank String ciudad,
        @NotBlank String departamento,
        @NotNull TipoDireccion tipo
) {}
