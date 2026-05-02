-- Creamos los tipos ENUM que usaremos más adelante
CREATE TYPE estado AS ENUM ('Libre', 'Ocupada', 'Limpieza', 'Mantenimiento');
CREATE TYPE categoria AS ENUM ('Medicina', 'Enfermeria', 'Auxiliar', 'Celador');
CREATE TYPE turno AS ENUM ('Mañana', 'Tarde', 'Noche');
CREATE TYPE sexo AS ENUM ('Hombre', 'Mujer', 'Otro');

-- Creamos las ENTIDADES y las RELACIONES
CREATE TABLE Habitacion (
id_habitacion SERIAL PRIMARY KEY,
numero INTEGER NOT NULL,
planta VARCHAR(50) NOT NULL,
estado estado NOT NULL
);

-- Entidad: Personal (Independiente)
CREATE TABLE Personal (
id_personal SERIAL PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
apellidos VARCHAR (100) NOT NULL,
categoria categoria NOT NULL,
turno turno NOT NULL
);

-- Entidad: Paciente (Depende de Habitacion) 
CREATE TABLE Paciente (
id_paciente SERIAL PRIMARY KEY,
-- Restricción UNIQUE: Dos pacientes no pueden compartir Número de Historia Clínica
nhc VARCHAR(20) UNIQUE NOT NULL,
nombre VARCHAR(50) NOT NULL,
apellidos VARCHAR (100) NOT NULL,
sexo sexo NOT NULL,
fecha_nacimiento DATE NOT NULL,

id_habitacion INTEGER NOT NULL,
-- ON DELETE RESTRICT: Por seguridad, no se puede borrar una habitación si tiene pacientes dentro
CONSTRAINT fk_habitacion
	FOREIGN KEY(id_habitacion)
	REFERENCES Habitacion(id_habitacion) ON DELETE RESTRICT
);

-- Entidad: Procedimiento (Tabla intermedia que relaciona Paciente y Personal)
CREATE TABLE Procedimiento (
id_procedimiento SERIAL PRIMARY KEY,
fecha_hora TIMESTAMP NOT NULL,
observaciones VARCHAR(300),

id_paciente INTEGER NOT NULL,
-- ON DELETE CASCADE: Facilitará el borrado íntegro de los datos del paciente desde la app
CONSTRAINT fk_paciente
	FOREIGN KEY(id_paciente)
	REFERENCES Paciente(id_paciente) ON DELETE CASCADE,

id_personal INTEGER NOT NULL,
-- ON DELETE RESTRICT: No puede ser eliminado de la BBDD si tiene procedimientos asociados
CONSTRAINT fk_personal
	FOREIGN KEY(id_personal)
	REFERENCES Personal(id_personal) ON DELETE RESTRICT
);

-- INTRODUCIMOS DATOS PARA PROBAR QUE TODO FUNCIONE --
-- INSERTAR HABITACIONES (Necesarias para los pacientes)
INSERT INTO habitacion (numero, planta, estado) VALUES 
(101, 'Planta 1 - Comba A', 'Libre'::estado),
(102, 'Planta 1 - Comba A', 'Ocupada'::estado),
(201, 'Planta 2 - Comba B', 'Limpieza'::estado);

-- INSERTAR PERSONAL (Necesario para los procedimientos)
INSERT INTO personal (nombre, apellidos, categoria, turno) VALUES 
('Daniel', 'Martin Hernandez', 'Enfermeria'::categoria, 'Mañana'::turno),
('Jorge', 'Chaves Cristobal', 'Medicina'::categoria, 'Tarde'::turno),
('Patricia', 'Serrano Torres', 'Auxiliar'::categoria, 'Noche'::turno);

-- INSERTAR PACIENTES (Referenciando a las habitaciones anteriores)
INSERT INTO paciente (nhc, nombre, apellidos, sexo, fecha_nacimiento, id_habitacion) VALUES 
('NHC778899', 'Kenneth', 'Méndez Pozo', 'Hombre'::sexo, '1985-05-20', 1),
('NHC112233', 'Ander', 'Resano Rey', 'Hombre'::sexo, '1992-11-03', 2),
('NHC445566', 'Joanne', 'Díaz Cano', 'Otro'::sexo, '1970-01-15', 1);

-- INSERTAR PROCEDIMIENTOS (Relacionamos pacientes e IDs de personal)
INSERT INTO procedimiento (fecha_hora, observaciones, id_paciente, id_personal) VALUES 
('2026-04-25 09:30:00', 'Administración de medicación pautada vía IV', 1, 1),
('2026-04-25 10:15:00', 'Revisión de analítica', 2, 2),
('2026-04-25 11:00:00', 'Cambio de vendaje y limpieza de herida.', 1, 3);


-- VERIFICAMOS QUE TODO ESTE BIEN INTRODUCIDO
SELECT * FROM habitacion;
SELECT * FROM personal;
SELECT * FROM paciente;
SELECT * FROM procedimiento;