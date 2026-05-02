package com.renerodriguez.core_client_management_system.application.port.in.command;

import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;
/**
 * Objeto de comando que encapsula los datos necesarios para la creación o actualización de una dirección.
 * <p>
 * Este record actúa como un DTO (Data Transfer Object) de entrada, permitiendo desacoplar
 * la estructura de los datos recibidos por los adaptadores de entrada de las entidades
 * de dominio internas.
 * </p>
 *
 * @param calle         Nombre de la vía, número de casa o indicadores específicos de la ubicación.
 * @param ciudad        Municipio o ciudad a la que pertenece la dirección.
 * @param departamento  Estado, provincia o departamento administrativo de la ubicación.
 * @param tipoDireccion Clasificación de la dirección (e.g., CASA, TRABAJO) definida en {@link TipoDireccion}.
 */
public record DireccionesCommand(
        String calle,
        String ciudad,
        String departamento,
        TipoDireccion tipoDireccion
) {
}
