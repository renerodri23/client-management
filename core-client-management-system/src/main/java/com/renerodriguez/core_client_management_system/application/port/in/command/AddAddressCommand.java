package com.renerodriguez.core_client_management_system.application.port.in.command;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;

import java.util.UUID;
/**
 * Comando para agregar una dirección a un usuario existente.
 * <p>
 * Este comando contiene la información necesaria para asociar una nueva
 * dirección a un usuario dentro del sistema.
 *
 * @param userUuid     el identificador único del usuario
 * @param calle        la calle de la dirección
 * @param ciudad       la ciudad de la dirección
 * @param departamento el departamento o región de la dirección
 * @param tipo         el tipo de dirección
 */
public record AddAddressCommand(
        UUID userUuid,
        String calle,
        String ciudad,
        String departamento,
        TipoDireccion tipo
) {}
