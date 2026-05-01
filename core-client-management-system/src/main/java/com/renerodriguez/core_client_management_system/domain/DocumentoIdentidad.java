package com.renerodriguez.core_client_management_system.domain;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad;

import java.util.UUID;

/**
 * Representa un documento de identidad asociado a un usuario en el sistema de gestión de clientes.
 * Contiene información sobre el tipo de documento (DUI, Pasaporte, NIT, NRC) y su valor correspondiente.
 */
public record DocumentoIdentidad(
        UUID uuidDoc,
        TipoDocumentoIdentidad tipo,
        String valor
) {

}
