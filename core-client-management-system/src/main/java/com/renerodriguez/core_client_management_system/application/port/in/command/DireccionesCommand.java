package com.renerodriguez.core_client_management_system.application.port.in.command;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;

public record DireccionesCommand(
        String calle,
        String ciudad,
        String departamento,
        TipoDireccion tipoDireccion
) {
}
