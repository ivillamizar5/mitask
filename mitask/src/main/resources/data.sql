-- Insertar usuarios
INSERT INTO usuario (correo_electronico, password ) VALUES
('juan@example.com','1234'),
('ana@example.com','12345' );

-- Insertar proyectos
INSERT INTO proyecto (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES
('Proyecto Alpha', 'Desarrollo de aplicación web', '2025-01-01', '2025-12-31', 'ACTIVO');

-- Asignar usuarios a proyectos (relación ManyToMany)
INSERT INTO proyecto_usuario (proyecto_id, usuario_id) VALUES
(1, 1),
(1, 2);

-- Insertar tareas
INSERT INTO tarea (nombre, descripcion, prioridad, estado, fecha_vencimiento, proyecto_id, usuario_id) VALUES
('Implementar inicio de sesión', 'Usar JWT para autenticar usuarios', 'ALTA', 'PENDIENTE', '2025-06-01', 1, 1),
('Diseñar interfaz de usuario', 'Crear prototipos de las vistas principales', 'MEDIA', 'EN_PROGRESO', '2025-07-01', 1, 2);