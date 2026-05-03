# Client Management System

Sistema integral para la administración y registro de clientes, diseñado bajo una **Arquitectura Hexagonal (Ports & Adapters)** para garantizar escalabilidad, mantenibilidad y un desacoplamiento efectivo de la infraestructura.

## Descripción del Proyecto

El sistema resuelve la necesidad de gestionar perfiles de clientes de forma dinámica, permitiendo el registro de múltiples direcciones y documentos de identificación. La solución integra una API REST robusta, un frontend moderno y una capa de persistencia segura.

### Requerimientos Implementados
* **Gestión Dinámica:** Registro ilimitado de direcciones y documentos por cliente.
* **Seguridad:** Autenticación y autorización basada en **JSON Web Tokens (JWT)**.
* **Auditoría:** Registro automático de cambios mediante **JPA Auditing** y trazabilidad por logs (Slf4j).
* **Reportes:** Generación de archivos descargables en formato **CSV** para administración.
* **Administración:** Panel exclusivo para usuarios con rol `ADMIN`.

---

## Tecnologías Utilizadas

### Backend (Spring Boot)
* **Java 17** con **Spring Boot 3**.
* **Spring Security:** Implementación de JWT para protección de endpoints.
* **Spring Data JPA:** Gestión de persistencia y auditoría de entidades.
* **MySQL 8.0:** Motor de base de datos relacional.
* **Lombok & MapStruct:** Optimización de código y mapeo eficiente de DTOs.

### Frontend (React)
* **React + Vite:** Entorno de desarrollo de alto rendimiento.
* **Redux Toolkit:** Gestión del estado global y persistencia de la sesión.
* **React Router Dom:** Navegación protegida por roles.


---

## Infraestructura y Despliegue en AWS

El proyecto ha sido desplegado utilizando una arquitectura de contenedores para garantizar la portabilidad.

* **Instancia:** Amazon EC2 (Amazon Linux 2023).
* **Orquestación:** Docker & Docker Compose.
* **Acceso Público:** http://ec2-3-22-167-120.us-east-2.compute.amazonaws.com:5173/login

Se configuraron **Security Groups** en AWS para permitir el tráfico entrante en los puertos necesarios (5173 para Frontend y 8080 para API), además de la gestión de volúmenes para la persistencia de datos en MySQL.

---

## Estructura de la API (Endpoints Principales)

### Gestión de Usuarios
| Método | Endpoint | Descripción | Acceso |
| :--- | :--- | :--- | :--- |
| POST | `/api/v1/users` | Registro de nuevo usuario/cliente | Público |
| GET | `/api/v1/users` | Listado general de clientes | ADMIN |
| GET | `/api/v1/users/{id}` | Detalle de cliente por UUID | Owner / ADMIN |
| GET | `/api/v1/users/reporte` | Descarga de reporte CSV | ADMIN |
| DELETE | `/api/v1/users/{id}` | Desactivación lógica de usuario | Owner / ADMIN |

### Direcciones y Documentación
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| POST | `/api/v1/users/{id}/addresses` | Agregar dirección adicional |
| POST | `/api/v1/users/{id}/documents` | Agregar documento de identificación |
| DELETE | `/api/v1/users/{uId}/addresses/{aId}` | Eliminar dirección específica |

---

## Navegación y Vistas

El Frontend implementa las siguientes rutas protegidas:

* **/login:** Pantalla de acceso con validación de credenciales.
* **/register:** Registro de nuevos clientes en el sistema.
* **/dashboard:** Vista principal con métricas o resumen del usuario.
* **/profile/:id:** Gestión detallada de perfiles, documentos y direcciones.
* **/admin:** Panel de control centralizado para la administración de clientes y descarga de reportes.

---

## Instalación Local

Si desea replicar el entorno de desarrollo localmente:

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/renerodri23/client-management.git

2. Levantar la infraestructura con Docker:
     ```bash
      docker-compose up -d --build
     ```

3. Acceder al sistema en: http://localhost:5173
