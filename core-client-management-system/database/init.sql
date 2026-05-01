CREATE DATABASE IF NOT EXISTS client_management_db;
USE client_management_db;

-- Estructura de tablas
CREATE TABLE IF NOT EXISTS users (
    id BINARY(16) PRIMARY KEY, 
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    user_role VARCHAR(100) NOT NULL DEFAULT 'USER',
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS user_addresses (
    id BINARY(16) PRIMARY KEY,
    user_id BINARY(16) NOT NULL,
    calle VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    departamento VARCHAR(100),
    tipo_direccion ENUM('CASA', 'TRABAJO', 'OTRO') NOT NULL,
    CONSTRAINT fk_user_address FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_documents (
    id BINARY(16) PRIMARY KEY,
    user_id BINARY(16) NOT NULL,
    tipo_documento ENUM('DUI', 'NIT', 'PASAPORTE') NOT NULL,
    valor_documento VARCHAR(50) NOT NULL,
    CONSTRAINT fk_user_document FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


-- 1. ADMIN (Login: admin@sistema.com / password123)
SET @admin_id = UUID();
INSERT INTO users (id, nombre, apellido, email, password_hash, user_role, created_by) 
VALUES (UUID_TO_BIN(@admin_id), 'Admin', 'Sistema', 'admin@sistema.com', '$2a$10$rz1wC47PGFzuIE6sNXTtw.ssMyvXqLhOgk/QEq4rNuZq4it.P3mVS', 'ADMIN', 'SYSTEM');

-- 2. CARLOS (Login: carlos@test.com / password123)
SET @carlos_id = UUID();
INSERT INTO users (id, nombre, apellido, email, password_hash, user_role, created_by) 
VALUES (UUID_TO_BIN(@carlos_id), 'Carlos', 'Gomez', 'carlos@test.com', '$2a$10$rz1wC47PGFzuIE6sNXTtw.ssMyvXqLhOgk/QEq4rNuZq4it.P3mVS', 'USER', 'ADMIN');

INSERT INTO user_addresses (id, user_id, calle, ciudad, departamento, tipo_direccion) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@carlos_id), 'Calle Las Magnolias 10', 'San Salvador', 'San Salvador', 'CASA'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@carlos_id), 'Centro Comercial Galerias', 'Escalón', 'San Salvador', 'TRABAJO');

INSERT INTO user_documents (id, user_id, tipo_documento, valor_documento) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@carlos_id), 'DUI', '01234567-8'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@carlos_id), 'NIT', '0614-123456-101-0'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@carlos_id), 'PASAPORTE', 'A12345678');

-- 3. MARIA (Login: maria@test.com / password123)
SET @maria_id = UUID();
INSERT INTO users (id, nombre, apellido, email, password_hash, user_role, created_by) 
VALUES (UUID_TO_BIN(@maria_id), 'Maria', 'Lopez', 'maria@test.com', '$2a$10$rz1wC47PGFzuIE6sNXTtw.ssMyvXqLhOgk/QEq4rNuZq4it.P3mVS', 'USER', 'ADMIN');

INSERT INTO user_addresses (id, user_id, calle, ciudad, departamento, tipo_direccion) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@maria_id), 'Residencial Santa Elena', 'Antiguo Cuscatlán', 'La Libertad', 'CASA'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@maria_id), 'Calle La Mascota 45', 'San Benito', 'San Salvador', 'OTRO');

INSERT INTO user_documents (id, user_id, tipo_documento, valor_documento) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@maria_id), 'DUI', '98765432-1');

-- 4. ROBERTO (Login: roberto@test.com / password123)
SET @roberto_id = UUID();
INSERT INTO users (id, nombre, apellido, email, password_hash, user_role, created_by) 
VALUES (UUID_TO_BIN(@roberto_id), 'Roberto', 'Sánchez', 'roberto@test.com', '$2a$10$rz1wC47PGFzuIE6sNXTtw.ssMyvXqLhOgk/QEq4rNuZq4it.P3mVS', 'USER', 'ADMIN');

INSERT INTO user_addresses (id, user_id, calle, ciudad, departamento, tipo_direccion) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@roberto_id), 'Barrio San Jacinto', 'San Salvador', 'San Salvador', 'CASA');

INSERT INTO user_documents (id, user_id, tipo_documento, valor_documento) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@roberto_id), 'DUI', '55555555-5'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@roberto_id), 'PASAPORTE', 'P9999999');

-- 5. ANA (Login: ana@test.com / password123)
SET @ana_id = UUID();
INSERT INTO users (id, nombre, apellido, email, password_hash, user_role, created_by) 
VALUES (UUID_TO_BIN(@ana_id), 'Ana', 'Martínez', 'ana@test.com', '$2a$10$rz1wC47PGFzuIE6sNXTtw.ssMyvXqLhOgk/QEq4rNuZq4it.P3mVS', 'USER', 'ADMIN');

INSERT INTO user_addresses (id, user_id, calle, ciudad, departamento, tipo_direccion) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@ana_id), 'Colonia Flor Blanca', 'San Salvador', 'San Salvador', 'CASA');

INSERT INTO user_documents (id, user_id, tipo_documento, valor_documento) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@ana_id), 'DUI', '11112222-3');

-- 6. PEDRO (Login pedro@test.com / password123)
SET @pedro_id = UUID();
INSERT INTO users (id, nombre, apellido, email, password_hash, user_role, created_by, active) 
VALUES (UUID_TO_BIN(@pedro_id), 'Pedro', 'Inactivo', 'pedro@test.com', '$2a$10$rz1wC47PGFzuIE6sNXTtw.ssMyvXqLhOgk/QEq4rNuZq4it.P3mVS', 'USER', 'ADMIN', 0);

INSERT INTO user_addresses (id, user_id, calle, ciudad, departamento, tipo_direccion) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@pedro_id), 'Calle Fantasma 0', 'San Salvador', 'San Salvador', 'CASA');

INSERT INTO user_documents (id, user_id, tipo_documento, valor_documento) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN(@pedro_id), 'DUI', '00000000-0');



