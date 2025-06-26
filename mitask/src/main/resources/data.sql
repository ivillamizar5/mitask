-- Limpiar tablas para evitar conflictos
DELETE FROM tarea;
DELETE FROM proyecto_usuario;
DELETE FROM proyecto;
DELETE FROM usuario;
DELETE FROM roles;

-- Insertar roles
INSERT INTO roles (nombre) VALUES
('ADMINISTRADOR'),
('COLABORADOR');

-- eimer@example.com - admin123
-- dario@example.com - col1234
-- anderson@example.com - col12345
-- Insertar usuarios (contraseñas encriptadas con BCrypt para "password123")
INSERT INTO usuario (correo_electronico, password, rol_id) VALUES
('eimer@example.com', '$2a$10$jEOBI7NSIIG.M.kfY0shPuB7LmtnNpAZA/nPFixwWJRMUfw/i6ka.', 1), -- Administrador
('dario@example.com', '$2a$10$gaqcWdL50qoib38Wg.P0veTgn4e5q0SoBLw/qOCNPPHRDdePA4gFG', 2),
('anderson@example.com', '$2a$10$VZT.cGqWxRYfVzddvlOZpOMb8kCWEYi4mFr79XL3FsBW94oSV4p.e', 2); -- Colaborador

-- Insertar proyectos
INSERT INTO proyecto (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES
('Proyecto Alpha', 'Desarrollo de aplicación web', '2025-01-01', '2025-12-31', 'ACTIVO'),
('Proyecto Beta', 'Implementación de API REST', '2025-02-01', '2025-11-30', 'ACTIVO');

-- Asignar usuarios a proyectos (relación muchos a muchos)
INSERT INTO proyecto_usuario (proyecto_id, usuario_id) VALUES
(1, 1), -- Juan en Proyecto Alpha
(1, 2), -- Ana en Proyecto Alpha
(2, 1); -- Juan en Proyecto Beta

-- Insertar tareas
INSERT INTO tarea (nombre, descripcion, prioridad, estado, fecha_vencimiento, proyecto_id, usuario_id) VALUES
('Implementar inicio de sesión', 'Usar JWT para autenticar usuarios', 'ALTA', 'PENDIENTE', '2025-06-01', 1, 1),
('Diseñar interfaz de usuario', 'Crear prototipos de las vistas principales', 'MEDIA', 'EN_PROGRESO', '2025-07-01', 1, 2),
('Optimizar base de datos', 'Mejorar consultas SQL', 'BAJA', 'PENDIENTE', '2025-08-01', 2, 1);