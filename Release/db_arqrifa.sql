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
    descripcion VARCHAR(200) NOT NULL,
    fecha DATETIME NOT NULL,
    duracion INT NOT NULL,
    obligatoria BIT NOT NULL,
    lugar VARCHAR(50) NOT NULL,
    observaciones VARCHAR(200) DEFAULT '',
    estado VARCHAR(15) DEFAULT 'Pendiente',
    eliminada BIT DEFAULT 0,
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
    resolucion VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_reunion) REFERENCES reuniones(id)
);

CREATE TABLE asistencias (
    id_reunion INT,
    ci INT,
    PRIMARY KEY (id_reunion , ci),
    FOREIGN KEY (id_reunion) REFERENCES reuniones (id),
    FOREIGN KEY (ci) REFERENCES usuarios (ci)
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
    FOREIGN KEY (id_reunion) REFERENCES reuniones(id)
);

CREATE TABLE propuestas (
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_encuesta INT NOT NULL,
    pregunta VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_encuesta) REFERENCES encuestas(id)
);

CREATE TABLE respuestas (
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_propuesta INT NOT NULL,
    respuesta VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_propuesta) REFERENCES propuestas(id)
);

CREATE TABLE votos (
        ci INT NOT NULL,
        id_respuesta INT NOT NULL,
        PRIMARY KEY(ci, id_respuesta),
        FOREIGN KEY (ci) REFERENCES usuarios(ci),
        FOREIGN KEY (id_respuesta) REFERENCES respuestas(id)
);




-- -------------------REGISTROS DE PRUEBA-------------------

INSERT INTO generaciones VALUES (0),(2015),(2016);

INSERT INTO usuarios(ci, id_gen, nombre, apellido, contrasena, email, rol) VALUES 
(4444444, 0, 'Luis', 'Pérez', '1234', 'luis@gmail.com', 'Admin'),
(5555555,2015, 'Juan', 'García', '1234', 'juanxxxxxxx@gmail.com', 'Estudiante'),
(6666666,2015, 'Mathías', 'Cabrera', '1234', 'mathixxxxxxx@gmail.com', 'Estudiante'),
(7777777,2015, 'Ana', 'Pérez', '1234', 'anaxxxxxxxxx@gmail.com', 'Encargado'),
(8888888,2015, 'Mathías', 'Gonzales', '1234', 'mathixxxx@gmail.com', 'Estudiante');

INSERT INTO solicitudes(ci, id_gen, fecha, nombre, apellido, contrasena, email, codigo, verificada) VALUES
(4444444, 2015, '2016-10-20', 'José', 'Artigas', '1234', 'jose@hotmail.com', 11111111, false),
(3333333, 2015, '2016-10-20', 'Mathias', 'Rodriguez', '1234', 'mathi@hotmail.com', 22222222, true);

INSERT INTO reuniones(id_gen, titulo, descripcion, fecha, duracion, obligatoria, lugar, observaciones, estado) VALUES
(2015,'Aumentar venta de rifas', 'En esta reunión se discutiran alternativas para aumentar la venta de rifas.', '2016-06-20 15:00:00', 120, 1, 'SALON 1', 'INGRESAR OBSERVACIÓN', 'Finalizada'),
(2015,'Bajar precio de rifas', 'En esta reunión se discutirá el nuevo precio de algunas rifas.', NOW(), 60, 0, 'SALON 2', '', 'Pendiente'),
(2015,'Aumentar venta de rifas', 'En esta reunión se discutiran alternativas para aumentar la venta de rifas.', '2016-12-20 15:00:00', 30, 0, 'SALON 3', '', 'Pendiente'),
(2015,'Fijación de precios de rifas', 'En esta reunión se discutirá el nuevo precio de algunas rifas.', '2017-06-20 15:00:00',60,1, 'SALON 4', '', 'Pendiente'),
(2016,'Aumentar venta de rifas', 'En esta reunión se discutiran alternativas para aumentar la venta de rifas.', '2016-06-20 15:00:00', 120, 1, 'SALON 1', 'INGRESAR OBSERVACIÓN', 'Finalizada'),
(2016,'Bajar precio de rifas', 'En esta reunión se discutirá el nuevo precio de algunas rifas.', NOW(), 60, 0, 'SALON 2', '', 'Pendiente'),
(2016,'Aumentar venta de rifas', 'En esta reunión se discutiran alternativas para aumentar la venta de rifas.', '2016-12-20 15:00:00', 30, 0, 'SALON 3', '', 'Pendiente'),
(2016,'Fijación de precios de rifas', 'En esta reunión se discutirá el nuevo precio de algunas rifas.', '2017-06-20 15:00:00',60,1, 'SALON 4', '', 'Pendiente');


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

INSERT INTO encuestas(id_reunion, titulo, duracion) VALUES
(1, 'Encuesta ...', 5),
(3, 'Encuesta del 20/6/16', 5),
(4, 'Encuesta de la reunion ID: 3', 5);

INSERT INTO propuestas (id_encuesta, pregunta) VALUES
(1, '¿Cuál de estos premios deberíamos incorporar?'),
(1, '¿Qué precio de rifa le parece mejor?'),
(2, '¿Cuál de estos premios deberíamos incorporar?'),
(2, '¿Qué precio de rifa le parece mejor?'),
(3, '¿Cuál de estos premios deberíamos incorporar?'),
(3, '¿Qué precio de rifa le parece mejor?');

INSERT INTO respuestas (id_propuesta, respuesta) VALUES
(1, 'Cámara Sony'),
(1, 'IPhone 6S'),
(1, 'giftcards en tienda inglesa valor $30.000'),
(2, '$3960'),
(2, '$3980'),
(2, '$3990'),
(2, '$3400'),
(3, 'Cámara Sony'),
(3, 'IPhone 6S'),
(3, 'Giftcards en tienda inglesa valor $30.000'),
(4, '$3960'),
(4, '$3980'),
(4, '$3990'),
(4, '$3400'),
(5, 'Cámara Sony'),
(5, 'IPhone 6S'),
(5, 'Giftcards en tienda inglesa valor $30.000'),
(6, '$3960'),
(6, '$3980'),
(6, '$3990'),
(6, '$3400');

INSERT INTO resoluciones(id_reunion, resolucion) VALUES(1, 'RESOLUCION 1');
INSERT INTO resoluciones(id_reunion, resolucion) VALUES(1, 'RESOLUCION 2');
INSERT INTO resoluciones(id_reunion, resolucion) VALUES(1, 'RESOLUCION 3');

INSERT INTO asistencias (id_reunion, ci) VALUES (1, 5555555), (1, 6666666);

INSERT INTO votos(id_respuesta, ci) VALUES(3, 5555555), (3, 6666666), (1, 8888888), (4, 5555555), (5, 6666666), (6, 8888888);


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

CREATE PROCEDURE ModificarUsuario(pCi int, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20))
BEGIN
	UPDATE usuarios SET nombre = pNombre, apellido = pApellido, contrasena = pContrasena WHERE ci = pCi;
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

-- retorno: -1 si ya existe una reunión para el mismo día
CREATE PROCEDURE ModificarReunion(pId int, pGeneracion int, pTitulo varchar(30), pDescripcion varchar(100), pFecha datetime, pDuracion int, pObligatoria bit, pLugar varchar(50), pEstado varchar(15), pObservaciones varchar(200), out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM reuniones WHERE CAST(Fecha AS DATE) = CAST(pFecha AS DATE) AND id_gen = pGeneracion AND id != pId) THEN
		SET retorno = -1;
	ELSE
		UPDATE reuniones SET titulo = pTitulo, descripcion = pDescripcion, fecha = pFecha, duracion = pDuracion, obligatoria = pObligatoria, lugar = pLugar, estado = pEstado, observaciones = pObservaciones WHERE id = pId;
	END IF;
END
$$

-- retorno 1 baja exitosa, -1 la reunión no existe o está en progreso todo: estos checkeos en lógica
CREATE PROCEDURE BajaReunion(pId int, out retorno int)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	IF NOT EXISTS (SELECT * FROM reuniones WHERE reuniones.id = pId AND reuniones.estado != 'Iniciada') THEN
		SET retorno = -1;
	ELSEIF EXISTS (SELECT * FROM reuniones WHERE reuniones.id = pId AND reuniones.estado = 'Pendiente') THEN
		START TRANSACTION;
        
        DELETE re.* FROM respuestas AS re 
			INNER JOIN propuestas AS p ON(re.id_propuesta = p.id)
			INNER JOIN encuestas AS e ON(p.id_encuesta = e.id)
			INNER JOIN reuniones AS r ON(e.id_reunion = r.id) 
            WHERE r.id = pId;
            
		DELETE p.* FROM propuestas AS p
			INNER JOIN encuestas AS e ON(p.id_encuesta = e.id)
			INNER JOIN reuniones AS r ON(e.id_reunion = r.id) 
            WHERE r.id = pId;
            

		DELETE FROM encuestas WHERE encuestas.id_reunion = pId;
        DELETE FROM temas WHERE temas.id_reunion = pId;
		DELETE FROM asistencias WHERE asistencias.id_reunion = pId;
		DELETE FROM reuniones WHERE reuniones.id = pId;
        
        SET retorno = 1;
        COMMIT;
    ELSE
		UPDATE reuniones SET eliminada = 1 WHERE reuniones.id = pId;
        SET retorno = 1;
    END IF;
END
$$

CREATE PROCEDURE AltaTema(pReunionId int, pTema varchar(50))
BEGIN
	INSERT INTO temas(id_reunion, tema) VALUES(pReunionId, pTema);
END
$$

CREATE PROCEDURE BajaTemas(pReunionId int)
BEGIN
	DELETE FROM temas WHERE id_reunion = pReunionId;
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

-- retorna 1 si la baja es exitosa
CREATE PROCEDURE BajaEncuesta(pId int, out retorno int)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	IF EXISTS (SELECT r.* FROM reuniones AS r INNER JOIN encuestas AS e on(r.id = e.id_reunion) WHERE e.id = pId AND r.estado = 'Finalizada') THEN
		SET retorno = -1;
	ELSE
		START TRANSACTION;
		DELETE r.* FROM respuestas AS r 
				INNER JOIN propuestas AS p ON(r.id_propuesta = p.id)
				INNER JOIN encuestas AS e ON(p.id_encuesta = e.id)
				WHERE e.id = pId;
		
		DELETE FROM propuestas WHERE id_encuesta = pId;
		DELETE FROM encuestas WHERE id = pId;
		
		SET retorno = 1;
		COMMIT;
    END IF;
END
$$

-- retorno -1 si la reunión está finalizada : verificar en lógica
CREATE PROCEDURE ModificarEncuesta(pId int, pTitulo varchar(30), pDuracion int, out retorno int)
BEGIN
	IF EXISTS (SELECT r.* FROM reuniones AS r INNER JOIN encuestas AS e on(r.id = e.id_reunion) WHERE e.id = pId AND r.estado = 'Finalizada') THEN
		SET retorno = -1;
	ELSE
		UPDATE Encuestas SET titulo = pTitulo, duracion = pDuracion WHERE id = pId;
	END IF;
END
$$


CREATE PROCEDURE AltaPropuesta(pEncuestaId int, pPregunta varchar(100), out pRetorno int)
BEGIN
	INSERT INTO propuestas(id_encuesta, pregunta) VALUES (pEncuestaId, pPregunta);
    SET pRetorno = LAST_INSERT_ID();
END
$$

CREATE PROCEDURE BajaPropuestas(pEncuestaId int)
BEGIN
	DELETE r.* FROM respuestas AS r INNER JOIN propuestas AS p ON(r.id_propuesta = p.id) INNER JOIN encuestas AS e ON(p.id_encuesta = e.id) WHERE e.id = pEncuestaId;
    DELETE FROM propuestas WHERE propuestas.id_encuesta = pEncuestaId;
END
$$


CREATE PROCEDURE AltaRespuesta(pPropuestaId int, pRespuesta varchar(100))
BEGIN
	INSERT INTO respuestas(id_propuesta, respuesta) VALUES (pPropuestaId, pRespuesta);
END
$$


CREATE PROCEDURE AltaVoto(pCi int, pRespuestaId int)
BEGIN
	INSERT INTO votos VALUES(pCi, pRespuestaId);
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

CREATE PROCEDURE BuscarUsuario(pCi int)
BEGIN
	SELECT * FROM usuarios WHERE ci = pCi;
END
$$



CREATE PROCEDURE BuscarSolicitud(pCi int)
BEGIN
	SELECT * FROM solicitudes WHERE ci = pCi;
END
$$

CREATE PROCEDURE BuscarReunion(pId int)
BEGIN
	SELECT * FROM reuniones WHERE id = pId AND eliminada = 0;
END
$$

CREATE PROCEDURE BuscarUltimaReunionPorGeneracion(pGenId int)
BEGIN
	SELECT * FROM reuniones WHERE id_gen = pGenId AND estado = 'Finalizada' AND eliminada = 0 ORDER BY fecha DESC LIMIT 1;
END
$$

CREATE PROCEDURE BuscarProximaReunionPorGeneracion(pGenId int)
BEGIN
	SELECT * FROM reuniones WHERE id_gen = pGenId AND estado = 'Pendiente' AND fecha >= DATE_FORMAT(NOW(),'%Y-%m-%d') ORDER BY fecha ASC LIMIT 1;
END
$$

CREATE PROCEDURE BuscarReunionActual(pGenId int)
BEGIN
	SELECT * FROM reuniones WHERE  id_gen = pGenId AND DATE_FORMAT(fecha,'%Y-%m-%d') =  DATE_FORMAT(NOW(),'%Y-%m-%d');
END
$$



CREATE PROCEDURE BuscarEncuesta(pId int)
BEGIN
	SELECT * FROM encuestas WHERE id = pId;
END
$$


CREATE PROCEDURE BuscarEncuestaDeReunion(pReunionId int)
BEGIN
	SELECT * FROM encuestas WHERE id_reunion = pReunionId;
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


CREATE PROCEDURE ListarUsuarios()
BEGIN
	SELECT * FROM usuarios WHERE id_gen != 0;
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

CREATE PROCEDURE ListarAsistenciasDeReunion(pReunionId int)
BEGIN
	SELECT * FROM asistencias WHERE id_reunion = pReunionId;
END
$$


CREATE PROCEDURE ListarReunionesTodas()
BEGIN
	SELECT * FROM reuniones ORDER BY fecha DESC;
END
$$

-- necesario?
CREATE PROCEDURE ListarReunionesDelDia()
BEGIN
	SELECT * FROM reuniones WHERE CAST(fecha AS DATE) >= CAST(CURDATE() AS DATE) AND eliminada = 0;
END
$$

-- necesario?
CREATE PROCEDURE ListarReunionesIniciadas()
BEGIN
	SELECT * FROM reuniones WHERE estado = 'Iniciada' AND eliminada = 0;
END
$$

CREATE PROCEDURE ListarReunionesPorGeneracion(pGen int)
BEGIN
	SELECT * FROM reuniones WHERE id_gen = pGen AND eliminada = 0 ORDER BY fecha DESC;
END
$$

CREATE PROCEDURE ListarReunionesIniciadasPorGeneracion(pGen int)
BEGIN
	SELECT * FROM reuniones WHERE estado = 'Iniciada' AND eliminada = 0;
END
$$

CREATE PROCEDURE ListarGeneraciones()
BEGIN
	SELECT * FROM generaciones WHERE id != 0;
END
$$

CREATE PROCEDURE ListarPropuestasDeEncuesta(pEncuestaId int)
BEGIN
	SELECT * FROM propuestas WHERE id_encuesta = pEncuestaId;
END
$$

CREATE PROCEDURE ListarRespuestasDePropuesta(pPropuestaId int)
BEGIN
	SELECT * FROM respuestas WHERE id_propuesta = pPropuestaId;
END
$$

CREATE PROCEDURE ListarRespuestasDePropuestaConVotos(pPropuestaId int)
BEGIN
	SELECT r.*, COUNT(v.id_respuesta) AS votos FROM respuestas AS r LEFT JOIN votos AS v ON(r.id = v.id_respuesta) WHERE id_propuesta = pPropuestaId GROUP BY id;
END
$$

DELIMITER ;
 -- SELECT * FROM reuniones PROCEDURE ANALYSE(); 
use arqrifa;