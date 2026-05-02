package com.renerodriguez.core_client_management_system.domain;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad;

import java.util.UUID;

/**
 * Representa un documento de identidad legal vinculado a un cliente en el dominio del sistema.
 * <p>
 * Este registro inmutable encapsula la identidad técnica del documento y su clasificación
 * legal según la normativa vigente (DUI, NIT, etc.).
 * </p>
 *
 * @param uuidDoc Identificador único universal del registro del documento en la base de datos.
 * @param tipo    Categoría del documento (e.g., DUI, Pasaporte, NIT) definida en {@link TipoDocumentoIdentidad}.
 * @param valor   Cadena de caracteres que representa el número o código alfanumérico del documento.
 */
public record DocumentoIdentidad(
        UUID uuidDoc,
        TipoDocumentoIdentidad tipo,
        String valor
) {

}
