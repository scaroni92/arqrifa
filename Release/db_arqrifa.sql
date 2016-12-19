DROP DATABASE IF EXISTS arqrifa;

CREATE DATABASE arqrifa;

USE arqrifa;


-- -------------------ESTRUCTURA DE TABLAS-------------------

CREATE TABLE generaciones(
	id INT PRIMARY KEY
);

CREATE TABLE usuarios (
    ci INT(7) PRIMARY KEY,
    id_gen INT(4) NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    contrasena VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL UNIQUE,
    rol VARCHAR(15) NOT NULL,
    FOREIGN KEY (id_gen) REFERENCES generaciones(id)
);

CREATE TABLE reuniones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_gen INT NOT NULL,
    titulo VARCHAR(30) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    fecha DATETIME NOT NULL,
    duracion INT NOT NULL,
    obligatoria BIT NOT NULL,
    lugar VARCHAR(50) NOT NULL,
    observaciones VARCHAR(100) DEFAULT '',
    estado VARCHAR(15) DEFAULT 'Pendiente',
    FOREIGN KEY (id_gen) REFERENCES generaciones(id)
);

CREATE TABLE temas (
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_reunion INT NOT NULL,
    tema VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_reunion) REFERENCES reuniones(id)
);

CREATE TABLE resoluciones (
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_reunion INT NOT NULL,
    resolucion VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_reunion) REFERENCES reuniones(id)
);

CREATE TABLE asistencias (
    id INT,
    ci INT,
    PRIMARY KEY (Id , Ci),
    FOREIGN KEY (Id) REFERENCES reuniones (Id),
    FOREIGN KEY (Ci) REFERENCES usuarios (Ci)
);

CREATE TABLE solicitudes (
    ci INT PRIMARY KEY,
    id_gen INT NOT NULL,
    fecha DATETIME NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    contrasena VARCHAR(20) NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL,
    codigo INT UNIQUE NOT NULL,
    verificada BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (id_gen) REFERENCES generaciones(id)
);

CREATE TABLE encuestas (
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_reunion INT NOT NULL UNIQUE,
    titulo VARCHAR(30) NOT NULL,
    duracion INT NOT NULL,
    estado VARCHAR(15) DEFAULT 'Pendiente',
    FOREIGN KEY (id_reunion) REFERENCES reuniones(id)
);

CREATE TABLE propuestas (
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_encuesta INT NOT NULL,
    pregunta VARCHAR(30) NOT NULL,
    FOREIGN KEY (id_encuesta) REFERENCES encuestas(id)
);

CREATE TABLE respuestas (
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_propuesta INT NOT NULL,
    respuesta VARCHAR(30) NOT NULL,
    FOREIGN KEY (id_propuesta) REFERENCES propuestas(id)
);


-- -------------------REGISTROS DE PRUEBA-------------------

INSERT INTO generaciones VALUES(0),(2010),(2012);

INSERT INTO usuarios(ci, id_gen, nombre, apellido, contrasena, email, rol) VALUES 
(4444444, 0, 'Luis', 'Pérez', '1234', 'luis@gmail.com', 'Admin'),
(5555555,2010, 'Juan', 'García', '1234', 'juanxxxxxxx@gmail.com', 'Estudiante'),
(7777777,2012, 'Ana', 'Pérez', '1234', 'anaxxxxxxxxx@gmail.com', 'Encargado');

INSERT INTO solicitudes(ci, id_gen, fecha, nombre, apellido, contrasena, email, codigo) VALUES
(4444444, 2012, '2016-10-20', 'José', 'Artigas', '1234', 'jose@hotmail.com', 11111111),
(3333333, 2012, '2016-10-20', 'Mathias', 'Rodriguez', '1234', 'mathi@hotmail.com', 22222222);

INSERT INTO reuniones(id_gen, titulo, descripcion, fecha, duracion, obligatoria, lugar, estado) VALUES
(2012,'Aumentar venta de rifas', 'En esta reunión se discutiran alternativas para aumentar la venta de rifas.', '2016-10-20 15:00:00', 120, 1, 'SALON 1', 'Pendiente'),
(2010,'Bajar precio de rifas', 'En esta reunión se discutirá el nuevo precio de algunas rifas.', '2016-06-20 15:00:00',60,0, 'SALON 2', 'Finalizada'),
(2012,'Aumentar venta de rifas', 'En esta reunión se discutiran alternativas para aumentar la venta de rifas.', '2016-12-20 15:00:00', 30, 0, 'SALON 3', 'Pendiente'),
(2012,'Fijación de precios de rifas', 'En esta reunión se discutirá el nuevo precio de algunas rifas.', NOW(),60,1, 'SALON 4', 'Pendiente');

INSERT INTO temas(id_reunion, tema) VALUES 
(1, 'Disminución de ventas'),
(1, 'Nuevos premios'),
(1, 'Fijación de nuevos precios'),
(2, 'Disminución de ventas'),
(2, 'Nuevos premios'),
(2, 'Fijación de nuevos precios'),
(3, 'Disminución de ventas'),
(3, 'Nuevos premios'),
(3, 'Fijación de nuevos precios'),
(4, 'Disminución de ventas'),
(4, 'Nuevos premios'),
(4, 'Fijación de nuevos precios');


-- -------------------PROCEDIMIENTOS ALMACENADOS-------------------

DELIMITER $$

--
-- GENERACIONES
--
CREATE PROCEDURE AltaGeneracion(pGeneracion int)
BEGIN
	INSERT INTO generaciones VALUES(pGeneracion);
END
$$

-- --------------------------------------------------------

--
-- USUARIOS
--

CREATE PROCEDURE AltaUsuario(pCi int, pGeneracion int, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(30), pRol varchar(15), out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM usuarios WHERE email = pEmail) THEN
		SET retorno = -1;
	ELSE
		INSERT INTO usuarios VALUES (pCi, pGeneracion, pNombre, pApellido, pContrasena, pEmail, pRol);
	END IF;
END
$$

CREATE PROCEDURE Autenticar(pCi int, pContrasena varchar(20))
BEGIN
	SELECT * FROM usuarios WHERE ci = pCi AND contrasena = pContraseña;
END
$$

-- --------------------------------------------------------

--
-- REUNIONES
--

-- retorno: -1 si ya existe una reunión para el mismo día
CREATE PROCEDURE AltaReunion(pGeneracion int, pTitulo varchar(30), pDescripcion varchar(100), pFecha datetime, pDuracion int, pObligatoria bit, pLugar varchar(50),out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM reuniones WHERE CAST(Fecha AS DATE) = CAST(pFecha AS DATE) AND id_gen = pGeneracion) THEN
		SET retorno = -1;
	ELSE
		INSERT INTO reuniones (id_gen, titulo, descripcion, fecha, duracion, obligatoria, lugar) VALUES (pGeneracion, pTitulo, pDescripcion, pFecha,pDuracion,pObligatoria, pLugar);
        SET retorno = LAST_INSERT_ID();
	END IF;
END
$$

CREATE PROCEDURE IniciarReunion(pId int)
BEGIN
	UPDATE reuniones SET estado = 'Iniciada' WHERE id = pId;
END
$$

CREATE PROCEDURE FinalizarReunion(pId int, pObservaciones varchar(100))
BEGIN
	UPDATE reuniones SET estado = 'Finalizada', observaciones = pObservaciones WHERE id = pId AND estado = 'Iniciada';
END
$$

CREATE PROCEDURE AltaTema(pReunionId int, pTema varchar(50))
BEGIN
	INSERT INTO temas(id_reunion, tema) VALUES(pReunionId, pTema);
END
$$

CREATE PROCEDURE AltaResolucion(pReunionId int, pResolucion varchar(50))
BEGIN
	INSERT INTO resoluciones (id_reunion, resolucion) VALUES(pReunionId, pResolucion);
END
$$

-- --------------------------------------------------------

--
-- ASISTENCIAS
--

CREATE PROCEDURE AltaAsistencia(pId int, pCi int)
BEGIN
	INSERT INTO asistencias VALUES (pId, pCi);
END
$$

-- --------------------------------------------------------

--
-- SOLICITUDES
--
-- retorno: -1 solicitudes.ci duplicado, -2 solicitudes.email duplicado, -3 usuarios.ci duplicado, -4 usuarios.email duplicado
CREATE PROCEDURE AltaSolicitud(pCi int, pGeneracion int, pFecha datetime, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(30), pCodigo int, out retorno int)
BEGIN
	IF EXISTS(SELECT * FROM solicitudes WHERE ci = pCi) THEN
		SET retorno = -1;
	ELSEIF EXISTS(SELECT * FROM solicitudes WHERE email = pEmail) THEN
		SET retorno = -2;
	ELSEIF EXISTS (SELECT * FROM usuarios WHERE ci = pCi) THEN
		SET retorno = -3;
	ELSEIF EXISTS (SELECT * FROM usuarios WHERE email = pEmail) THEN
		SET retorno = -4;

	ELSE
		INSERT INTO solicitudes VALUES (pCi,pGeneracion, pFecha, pNombre, pApellido, pContrasena, pEmail, pCodigo, 0);
	END IF;
END
$$

CREATE PROCEDURE VerificarSolicitud(pCodigo int)
BEGIN
	UPDATE solicitudes SET verificada = 1 WHERE codigo = pCodigo;
END
$$

CREATE PROCEDURE ConfirmarSolicitud(pCi int, pGeneracion int, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(30))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;
    DELETE FROM solicitudes WHERE ci = pCi;
    INSERT INTO usuarios VALUES(pCi, pGeneracion, pNombre, pApellido, pContrasena, pEmail, 'Estudiante');
	COMMIT;
END
$$

CREATE PROCEDURE EliminarSolicitud(pCi int)
BEGIN
	DELETE FROM solicitudes WHERE ci = pCi;
END
$$

-- --------------------------------------------------------

--
-- ENCUESTAS
--

CREATE PROCEDURE AltaEncuesta(pReunionId int, pTitulo varchar(30), pDuracion int, out pRetorno int)
BEGIN
	INSERT INTO encuestas(id_reunion, titulo, duracion) VALUES(pReunionId, pTitulo, pDuracion);
    SET pRetorno = LAST_INSERT_ID();
END
$$

CREATE PROCEDURE AltaPropuesta(pEncuestaId int, pPregunta varchar(30), out pRetorno int)
BEGIN
	INSERT INTO propuestas(id_encuesta, pregunta) VALUES (pEncuestaId, pPregunta);
    SET pRetorno = LAST_INSERT_ID();
END
$$

CREATE PROCEDURE AltaRespuesta(pPropuestaId int, pRespuesta varchar(30))
BEGIN
	INSERT INTO respuestas(id_propuesta, respuesta) VALUES (pPropuestaId, pRespuesta);
END
$$

-- --------------------------------------------------------

--
-- BÚSQUEDAS
--

CREATE PROCEDURE BuscarEstudiante(pCi int)
BEGIN
	SELECT * FROM usuarios WHERE ci = pCi AND rol = 'Estudiante';
END
$$

CREATE PROCEDURE BuscarSolicitud(pCi int)
BEGIN
	SELECT * FROM solicitudes WHERE ci = pCi;
END
$$

CREATE PROCEDURE BuscarReunion(pId int)
BEGIN
	SELECT * FROM reuniones WHERE id = pId;
END
$$

-- --------------------------------------------------------

--
-- LISTADOS
--
CREATE PROCEDURE ListarEstudiantesPorGeneracion(pGeneracion int)
BEGIN
	SELECT * FROM usuarios WHERE rol = 'Estudiante' AND id_gen = pGeneracion;
END
$$

CREATE PROCEDURE ListarSolicitudesPorGeneracion(pGen int)
BEGIN
	SELECT * FROM solicitudes WHERE id_gen = pGen;
END
$$

CREATE PROCEDURE ListarTemasDeReunion(pReunionId int)
BEGIN
	SELECT * FROM temas WHERE id_reunion = pReunionId;
END
$$

CREATE PROCEDURE ListarResolucionesDeReunion(pReunionId int)
BEGIN
	SELECT * FROM resoluciones WHERE id_reunion = pReunionId;
END
$$

CREATE PROCEDURE ListarReunionesDelDia()
BEGIN
	SELECT * FROM reuniones WHERE CAST(fecha AS DATE) >= CAST(CURDATE() AS DATE);
END
$$

CREATE PROCEDURE ListarReunionesIniciadas()
BEGIN
	SELECT * FROM reuniones WHERE estado = 'Iniciada';
END
$$

CREATE PROCEDURE ListarReunionesIniciadasPorGeneracion(pGen int)
BEGIN
	SELECT * FROM reuniones WHERE estado = 'Iniciada';
END
$$

CREATE PROCEDURE ListarGeneraciones()
BEGIN
	SELECT * FROM generaciones WHERE id != 0;
END
$$






DELIMITER ;

 -- SELECT * FROM reuniones PROCEDURE ANALYSE();