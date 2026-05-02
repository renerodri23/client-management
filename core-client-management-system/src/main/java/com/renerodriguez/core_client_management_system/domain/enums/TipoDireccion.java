package com.renerodriguez.core_client_management_system.domain.enums;
/**
 * Define las categorías de ubicación física que pueden asignarse a las direcciones de un usuario.
 * <p>
 * Este enumerado permite clasificar las direcciones según su propósito o naturaleza,
 * facilitando la segmentación de la información de contacto en el dominio.
 * </p>
 * * @see #CASA Dirección de residencia principal del usuario.
 * @see #TRABAJO Dirección vinculada al entorno laboral o profesional.
 * @see #OTRO Cualquier otra ubicación que no se ajuste a las categorías anteriores.
 */
public enum TipoDireccion {
    CASA, TRABAJO, OTRO
}
