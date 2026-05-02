package com.renerodriguez.core_client_management_system.application.port.in.command;

/**
 * Objeto de comando para la transferencia de datos de documentos de identidad.
 * <p>
 * Este record se utiliza para encapsular la información mínima necesaria al momento de
 * registrar o actualizar un documento legal asociado a un usuario, abstrayendo la
 * persistencia de la entrada de datos.
 * </p>
 *
 * @param tipo  El tipo de documento legal (e.g., DUI, NIT, PASAPORTE) definido por {@link com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad}.
 * @param valor El número, código o identificador alfanumérico único del documento.
 */
public record DocumentoCommand(
        com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad tipo,
        String valor
) {
}
