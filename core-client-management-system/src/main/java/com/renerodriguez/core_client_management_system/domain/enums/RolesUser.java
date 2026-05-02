package com.renerodriguez.core_client_management_system.domain.enums;
/**
 * Define los niveles de acceso y privilegios disponibles para los usuarios en el sistema.
 * <p>
 * Este enumerado es utilizado para el control de acceso basado en roles (RBAC),
 * determinando qué acciones puede ejecutar un usuario sobre los recursos del dominio.
 * </p>
 * * @see #USER Rol con permisos básicos para la gestión de su propio perfil.
 * @see #ADMIN Rol con privilegios elevados para la administración global del sistema.
 */
public enum RolesUser {
    USER, ADMIN
}
