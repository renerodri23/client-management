package com.renerodriguez.core_client_management_system.application.port.in.command;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;

import java.util.UUID;

public record AddAddressCommand(
        UUID userUuid,
        String calle,
        String ciudad,
        String departamento,
        TipoDireccion tipo
) {}
