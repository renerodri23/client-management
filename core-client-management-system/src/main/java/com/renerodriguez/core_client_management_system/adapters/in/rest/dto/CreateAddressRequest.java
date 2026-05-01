package com.renerodriguez.core_client_management_system.adapters.in.rest.dto;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAddressRequest(
        @NotBlank String calle,
        @NotBlank String ciudad,
        @NotBlank String departamento,
        @NotNull TipoDireccion tipo
) {}
