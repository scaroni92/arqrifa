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
    email VARCHAR(50) NOT NULL UNIQUE,
    rol VARCHAR(15) NOT NULL,
    FOREIGN KEY (id_gen) REFERENCES generaciones(id)
);

CREATE TABLE reuniones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_gen INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha DATETIME NOT NULL,
    duracion INT NOT NULL,
    obligatoria BIT NOT NULL,
    lugar VARCHAR(100) NOT NULL,
    observaciones TEXT,
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
    resolucion TEXT NOT NULL,
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
    email VARCHAR(50) UNIQUE NOT NULL,
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

INSERT INTO generaciones VALUES (0),(2015);

INSERT INTO usuarios(ci, id_gen, nombre, apellido, contrasena, email, rol) VALUES 
(4444444, 0, 'Luis', 'Pérez', '1234', 'luis.perez.adminx@gmail.com', 'Admin'),
(7777777, 2015, 'Ana', 'Pérez', '1234', 'anaperezxxxxxxxxxxxx@gmail.com', 'Encargado'),
(5555551, 2015, 'Facundo', 'García', '1234', 'facundoxxxxxxxx@gmail.com', 'Estudiante'),
(5555552, 2015, 'Melanie', 'García', '1234', 'melaniexxxxxxxx@gmail.com', 'Estudiante'),
(5555553, 2015, 'Alejandro', 'Correa', '1234', 'alejaxxxxxxxx@gmail.com', 'Estudiante'),
(5555554, 2015, 'Nicolas', 'García', '1234', 'nicolasxxxxxxxx@gmail.com', 'Estudiante'),
(5555555, 2015, 'Luis', 'Gonzales', '1234', 'lugonzalxxxxxexx@gmail.com', 'Estudiante'),
(5555556, 2015, 'Juan', 'García', '1234', 'juanxxxxxxxxxxxxxx@gmail.com', 'Estudiante'),
(5555557, 2015, 'Ezquiel', 'Pérez', '1234', 'ezquielxxxxxxxxx@gmail.com', 'Estudiante'),
(5555558, 2015, 'Raúl', 'Gomez', '1234', 'raulgomezxxxxxxxxxx@gmail.com', 'Estudiante'),
(5555559, 2015, 'Mathías', 'Gonzales', '1234', 'mathxxsxxxxxx@gmail.com', 'Estudiante'),
(5555550, 2015, 'Miguel', 'Cabrera', '1234', 'miguelxxxxxxxxx@gmail.com', 'Estudiante');


INSERT INTO solicitudes(ci, id_gen, fecha, nombre, apellido, contrasena, email, codigo, verificada) VALUES
(4444444, 2015, '2016-10-20', 'Tabaré', 'Rivero', '1234', 'latabarr@taba.com', 11111111, false),
(3333333, 2015, '2016-10-20', 'Tabaré', 'Cardozo', '1234', 'tabarecardozo@taba.com', 22222222, true);

INSERT INTO reuniones(id_gen, titulo, descripcion, fecha, duracion, obligatoria, lugar, observaciones, estado) VALUES
(2015,'ASAMBLEA GENERAL OBLIGATORIA', 'Se dará comienzo a la reunión presentando a los integrantes de las diferentes comisiones.', '2016-05-22 15:00:00', 120, 1, 'Salón de Actos', 'Se informan los medios oficiales de comunicación de la comisión: proyecto.arquiectura.rifa@gmail.com', 'Finalizada'),
(2015,'REUNIÓN GENERAL OBLIGATORIA', 'Se dará comienzo a la reunión presentando a los integrantes de las diferentes comisiones.', NOW(), 60, 1, 'Salón 2', '', 'Pendiente'),
(2015,'ASAMBLEA OBLIGATORIA', 'Se dará comienzo a la reunión mencionando la situación actual de las rifas.', '2017-10-20 15:00:00', 30, 1, 'Salón 3', '', 'Pendiente'),
(2015,'ASAMBLEA OBLIGATORIA', 'Se dará comienzo a la reunión mencionando la situación actual de las rifas.', '2017-11-20 15:00:00',60,1, 'Salón 4', '', 'Pendiente'),
(2015,'ASAMBLEA', 'Se dará comienzo a la reunión mencionando la situación actual de las rifas.', '2017-12-20 15:00:00', 60, 0, 'Salón 2', '', 'Pendiente'),
(2015,'REUNIÓN OBLIGATORIA', 'Se dará comienzo a la reunión mencionando la situación actual de las rifas.', '2018-01-20 15:00:00', 120, 1, 'Salón 1', '', 'Pendiente');

INSERT INTO temas(id_reunion, tema) VALUES 
(1, 'Reglamento'),
(1, 'Banco'),
(1, 'Rifas'),
(2, 'Banco'),
(2, 'Rifas'),
(3, 'Bancos'),
(3, 'Rifas'),
(4, 'Banco'),
(4, 'Rifas'),
(4, 'Premios'),
(5, 'Rifas'),
(6, 'Rifas');

INSERT INTO encuestas(id_reunion, titulo, duracion) VALUES
(1, 'Encuesta de reglamento', 15),
(3, 'Encuesta de rifas', 15),
(4, 'Encuesta de rifas', 15);

INSERT INTO propuestas (id_encuesta, pregunta) VALUES
(1, '¿Está a favor del reglamento?'),
(2, '¿Cuál de estos premios deberíamos incorporar?'),
(2, '¿Cuál precio de rifa cree más adecuado?'),
(3, '¿Cuál de estos premios deberíamos incorporar?'),
(3, '¿Cuál precio de rifa cree más adecuado?');

INSERT INTO respuestas (id_propuesta, respuesta) VALUES
(1, 'Estoy a favor'),
(1, 'No estoy a favor'),
(2, 'Cámara Sony'),
(2, 'IPhone 6S'),
(2, 'Giftcards en tienda inglesa valor $30.000'),
(3, '$3960'),
(3, '$3980'),
(3, '$3990'),
(3, '$3400'),
(4, 'Cámara Sony'),
(4, 'IPhone 6S'),
(4, 'Giftcards en tienda inglesa valor $30.000'),
(5, '$3960'),
(5, '$3980'),
(5, '$3990'),
(5, '$3400');

INSERT INTO resoluciones(id_reunion, resolucion) VALUES(1, 'Reglamento: Se da lectura al reglamento por parte de la Coordinadora. Se vota a favor del mismo, sin ningún tipo de objeción, con el 100% de los votos a favor.');
INSERT INTO resoluciones(id_reunion, resolucion) VALUES(1, 'Bancos: Se explica el proceso de selección de banco.');
INSERT INTO resoluciones(id_reunion, resolucion) VALUES(1, 'Rifas: Se habla rápidamente de las rifas, de su valor, la cantidad para cada integrante de la generación, y aproximación de la fecha de entrega de las mismas.');

INSERT INTO asistencias (id_reunion, ci) VALUES (1, 5555551), (1, 5555552), (1, 5555553), (1, 5555554), (1, 5555555), (1, 5555556), (1, 5555557), (1, 5555558), (1, 5555559);

INSERT INTO votos(id_respuesta, ci) VALUES (1, 5555551), (1, 5555552), (1, 5555553), (1, 5555554), (1, 5555555), (1, 5555556), (1, 5555557), (1, 5555558), (1, 5555559), (1, 5555550);

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

CREATE PROCEDURE AltaUsuario(pCi int, pGeneracion int, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(50), pRol varchar(15), out retorno int)
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
CREATE PROCEDURE AltaReunion(pGeneracion int, pTitulo varchar(100), pDescripcion text, pFecha datetime, pDuracion int, pObligatoria bit, pLugar varchar(100),out retorno int)
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
CREATE PROCEDURE ModificarReunion(pId int, pGeneracion int, pTitulo varchar(100), pDescripcion text, pFecha datetime, pDuracion int, pObligatoria bit, pLugar varchar(100), pEstado varchar(15), pObservaciones text, out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM reuniones WHERE CAST(Fecha AS DATE) = CAST(pFecha AS DATE) AND id_gen = pGeneracion AND id != pId) THEN
		SET retorno = -1;
	ELSE
		UPDATE reuniones SET titulo = pTitulo, descripcion = pDescripcion, fecha = pFecha, duracion = pDuracion, obligatoria = pObligatoria, lugar = pLugar, estado = pEstado, observaciones = pObservaciones WHERE id = pId;
	END IF;
END
$$

CREATE PROCEDURE CambiarEstadoReunion(pId int, pEstado varchar(15))
BEGIN
	UPDATE reuniones SET estado = pEstado WHERE id = pId;
END
$$

-- retorno 1 baja exitosa
CREATE PROCEDURE BajaReunion(pId int, out retorno int)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	IF EXISTS (SELECT * FROM reuniones WHERE reuniones.id = pId AND reuniones.estado = 'Pendiente') THEN
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

CREATE PROCEDURE AltaResolucion(pReunionId int, pResolucion text)
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
CREATE PROCEDURE AltaSolicitud(pCi int, pGeneracion int, pFecha datetime, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(50), pCodigo int, out retorno int)
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

CREATE PROCEDURE ConfirmarSolicitud(pCi int, pGeneracion int, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(50))
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

CREATE PROCEDURE BajaEncuesta(pId int, out retorno int)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;
	
    DELETE r.* FROM respuestas AS r 
			INNER JOIN propuestas AS p ON(r.id_propuesta = p.id)
			INNER JOIN encuestas AS e ON(p.id_encuesta = e.id)
			WHERE e.id = pId;
		
	DELETE FROM propuestas WHERE id_encuesta = pId;
	DELETE FROM encuestas WHERE id = pId;
		
	SET retorno = 1;
	COMMIT;
END
$$

CREATE PROCEDURE ModificarEncuesta(pId int, pTitulo varchar(30), pDuracion int)
BEGIN
	UPDATE Encuestas SET titulo = pTitulo, duracion = pDuracion WHERE id = pId;
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

CREATE PROCEDURE BuscarVoto(pCi int, pEncuestaId int)
BEGIN
	SELECT r.* FROM votos AS v 
		INNER JOIN respuestas AS r ON(r.id = v.id_respuesta)
        INNER JOIN propuestas AS p ON(p.id = r.id_propuesta)
        INNER JOIN encuestas AS e ON(e.id = p.id_encuesta)
        WHERE v.ci = pCi AND e.id = pEncuestaId;
END
$$

-- --------------------------------------------------------

--
-- BÚSQUEDAS
--

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

GRANT all ON *.* TO root@192.168.10.241 IDENTIFIED BY 'password';
FLUSH PRIVILEGES;
