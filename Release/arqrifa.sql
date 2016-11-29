DROP DATABASE IF EXISTS arqrifa;

CREATE DATABASE arqrifa;

USE arqrifa;


CREATE TABLE generaciones(
	genId INT PRIMARY KEY
);

CREATE TABLE usuarios (
    Ci INT PRIMARY KEY,
    Generacion INT NOT NULL,
    Nombre VARCHAR(20) NOT NULL,
    Apellido VARCHAR(20) NOT NULL,
    Contrasena VARCHAR(20) NOT NULL,
    Email VARCHAR(30) NOT NULL,
    Rol VARCHAR(15) NOT NULL,
    FOREIGN KEY (Generacion) REFERENCES generaciones(genId)
);

CREATE TABLE reuniones (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Generacion INT NOT NULL,
    Titulo VARCHAR(30) NOT NULL,
    Descripcion VARCHAR(100) NOT NULL,
    Fecha DATETIME NOT NULL,
    Duracion INT NOT NULL,
    Obligatoria BIT NOT NULL,
    Lugar VARCHAR(50) NOT NULL,
    Observaciones VARCHAR(100) DEFAULT '',
    Estado VARCHAR(15) DEFAULT 'Pendiente',
    FOREIGN KEY (Generacion) REFERENCES generaciones(genId)
);

CREATE TABLE temas (
	Id INT PRIMARY KEY AUTO_INCREMENT,
    ReunionId INT NOT NULL,
    Tema VARCHAR(50) NOT NULL,
    FOREIGN KEY (ReunionId) REFERENCES reuniones(Id)
);

CREATE TABLE resoluciones (
	Id INT PRIMARY KEY AUTO_INCREMENT,
    ReunionId INT NOT NULL,
    Resolucion VARCHAR(50) NOT NULL,
    FOREIGN KEY (ReunionId) REFERENCES reuniones(Id)
);


CREATE TABLE asistencias (
    Id INT,
    Ci INT,
    FOREIGN KEY (Id) REFERENCES reuniones (Id),
    FOREIGN KEY (Ci) REFERENCES usuarios (Ci),
    PRIMARY KEY (Id , Ci)
);

CREATE TABLE solicitudes (
    Ci INT PRIMARY KEY,
    Generacion INT NOT NULL,
    Fecha DATETIME NOT NULL,
    Nombre VARCHAR(20) NOT NULL,
    Apellido VARCHAR(20) NOT NULL,
    Contrasena VARCHAR(20) NOT NULL,
    Email VARCHAR(30) UNIQUE NOT NULL,
    Codigo INT UNIQUE NOT NULL,
    Verificada BIT NOT NULL,
    FOREIGN KEY (Generacion) REFERENCES generaciones(genId)
);

DELIMITER $$
-- GENERACIONES
-- AltaGeneracion  - Da de alta una generacion
-- Retorno : -1 si ya existe
CREATE PROCEDURE AltaGeneracion(pGenId int,out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM generaciones WHERE genId = pGenId) THEN
		SET retorno = -1;
	ELSE
		INSERT INTO generaciones VALUES(pGenId);
	END IF;
END
$$


-- USUARIOS
-- AltaUsuario  - Da de alta un usuario
-- Retorno : -1 si ya existe la ci, -2 si ya existe el email y -3 si no existe la generación
CREATE PROCEDURE AltaUsuario(pCi int, pGeneracion int, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(30), pRol varchar(15),out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM usuarios WHERE ci = pCi) THEN
		SET retorno = -1;
	ELSEIF EXISTS (SELECT * FROM usuarios WHERE email = pEmail) THEN
		SET retorno = -2;
	ELSEIF NOT EXISTS(SELECT * FROM generaciones WHERE genId = pGeneracion) THEN
		SET retorno = -3;
	ELSE
		INSERT INTO usuarios VALUES (pCi,pGeneracion, pNombre, pApellido, pContrasena, pEmail, pRol);
	END IF;
END
$$

-- Autenticar - Busca el usuario con las credenciales dadas
CREATE PROCEDURE Autenticar(pCi int, pContrasena varchar(20))
BEGIN
	SELECT * FROM usuarios WHERE Ci = pCi AND contrasena = pContraseña;
END
$$


-- BuscarEstudiante - Busca el estudiante con las credenciales dadas
CREATE PROCEDURE BuscarEstudiante(pCi int)
BEGIN
	SELECT * FROM usuarios WHERE ci = pCi AND rol = 'Estudiante';
END
$$

-- ListarEstudiantes - Devuelve los estudiantes de una generación
CREATE PROCEDURE ListarEstudiantes(pGeneracion int)
BEGIN
	SELECT * FROM usuarios WHERE rol = 'Estudiante' AND generacion = pGeneracion;
END
$$

-- REUNIONES
-- AltaReunion  - Da de alta una reunion
-- Retorno : -1 si existe una reunion para dicha generacion en el mismo dia
CREATE PROCEDURE AltaReunion(pGeneracion int, pTitulo varchar(30), pDescripcion varchar(100), pFecha datetime, pDuracion int, pObligatoria bit, pLugar varchar(50),out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM reuniones WHERE CAST(Fecha as date) = CAST(pFecha as date)) THEN
		SET retorno = -1;
	ELSE
		INSERT INTO reuniones (Generacion,Titulo,Descripcion,Fecha,Duracion,Obligatoria,Lugar) VALUES (pGeneracion, pTitulo, pDescripcion, pFecha,pDuracion,pObligatoria, pLugar);
        SET retorno = LAST_INSERT_ID();
	END IF;
END
$$

CREATE PROCEDURE AltaTema(pReunionId int, pTema varchar(50))
BEGIN
	INSERT INTO temas (ReunionId, Tema) VALUES(pReunionId, pTema);
END
$$

CREATE PROCEDURE ListarTemas(pReunionId int)
BEGIN
	SELECT * FROM temas WHERE ReunionId = pReunionId;
END
$$

CREATE PROCEDURE AltaResolucion(pReunionId int, pResolucion varchar(50))
BEGIN
	INSERT INTO resoluciones (ReunionId, Resolucion) VALUES(pReunionId, pResolucion);
END
$$

CREATE PROCEDURE ListarResoluciones(pReunionId int)
BEGIN
	SELECT * FROM resoluciones WHERE ReunionId = pReunionId;
END
$$
-- BuscarReunion - Busca una reunion por ID
CREATE PROCEDURE BuscarReunion(pId int)
BEGIN
	SELECT * FROM reuniones WHERE id = pId;
end
$$

-- ListarReunionesDelDia  - Devuelve la lista de reuiones del dia de hoy
CREATE PROCEDURE ListarReunionesDelDia()
BEGIN
	SELECT * FROM reuniones WHERE CAST(Fecha as date) >= CAST(CURDATE() as date);
END
$$

CREATE PROCEDURE ListarReunionesIniciadas()
BEGIN
	SELECT * FROM reuniones WHERE estado = 'Iniciada';
END
$$

-- IniciarReunion - Marca una reunión como iniciada
CREATE PROCEDURE IniciarReunion(pId int)
BEGIN
	UPDATE reuniones SET estado = 'Iniciada' WHERE id = pId;
END
$$


-- Verificar que la reunión sea pendiente e iniciada

-- FinalizarReunion - Marca una reunión como finalizada
-- Retorno : -1 si la reunión no existe

CREATE PROCEDURE FinalizarReunion(pId int, pObservaciones varchar(100), out retorno int)
BEGIN
	IF NOT EXISTS (SELECT * FROM reuniones WHERE id = pId) THEN
		SET retorno = -1;
	ELSE
		UPDATE reuniones SET estado = 'Finalizada', observaciones = pObservaciones WHERE id = pId AND estado = 'Iniciada';
	END IF;
END
$$

-- ASISTENCIAS
-- MarcarAsistencia - Marca la asistencia de un estudiante a una reunion
-- Retorno : -1 si ya tiene asistencia, -2 si la reunion no existe , -3 si el estudiante no existe
CREATE PROCEDURE MarcarAsistencia(pId int, pCi int,out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM asistencias WHERE Id = pId and Ci = pCi) THEN
		SET retorno = -1;
	ELSEIF NOT EXISTS (SELECT * FROM reuniones WHERE Id = pId) THEN
		SET retorno = -2;
	ELSEIF NOT EXISTS (SELECT * FROM usuarios WHERE Ci = pCi AND Rol = 'Estudiante') THEN
		SET retorno = -3;
	ELSE
		INSERT INTO asistencias VALUES (pId,pCi);
	END IF;
END
$$


-- SOLICITUDES
-- AltaSolicitud  - Da de alta una solicitud
-- Retorno : -1 si la ci ya existe en la tabla solicitudes, -2 el email ya existe en la tabla solicitudes, -3 la ci ya existe en la tabla usuarios, -4 el email ya existe en la tabla usuarios
CREATE PROCEDURE AltaSolicitud(pCi int, pGeneracion int, pFecha DATETIME, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(30), pCodigo int, out retorno int)
BEGIN
	IF EXISTS(SELECT * FROM solicitudes WHERE Ci = pCi) THEN
		SET retorno = -1;
	ELSEIF EXISTS(SELECT * FROM solicitudes WHERE Email = pEmail) THEN
		SET retorno = -2;
	ELSEIF EXISTS (SELECT * FROM usuarios WHERE Ci = pCi AND Rol = 'Estudiante') THEN
		SET retorno = -3;
	ELSEIF EXISTS (SELECT * FROM usuarios WHERE Email = pEmail) THEN
		SET retorno = -4;

	ELSE
		INSERT INTO solicitudes VALUES (pCi,pGeneracion, pFecha, pNombre, pApellido, pContrasena, pEmail, pCodigo, FALSE);
	END IF;
END
$$

-- ListarSolicitudesDeGeneracion  - Devuelve la lista de solicitudes de una generación
CREATE PROCEDURE ListarSolicitudesDeGeneracion(pGen int)
BEGIN
	SELECT * FROM solicitudes WHERE solicitudes.Generacion = pGen;
END
$$

-- VerificarSolicitud - Marca la solicitud como verificada
-- Retorno : cantidad de filas afectadas
CREATE PROCEDURE VerificarSolicitud(pCodigo int)
BEGIN 
	UPDATE solicitudes SET verificada = true WHERE codigo = pCodigo;
END
$$

-- ConfirmarSolicitud - Da de alta al usuario y luego elimina la solicitud
-- Retorno : -1 si no existe la solicitud

CREATE PROCEDURE ConfirmarSolicitud(pCi int, pGeneracion int, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(30), out retorno int)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;
	SET SQL_SAFE_UPDATES = 0;
	IF EXISTS (SELECT * FROM solicitudes WHERE ci = pCi) THEN
		INSERT INTO usuarios VALUES(pCi, pGeneracion, pNombre, pApellido, pContrasena, pEmail, 'Estudiante');
		DELETE FROM solicitudes WHERE ci = pCi;
	ELSE 
		SET retorno = -1;
	END IF;
	SET SQL_SAFE_UPDATES = 1;
	COMMIT;
END
$$

CREATE PROCEDURE EliminarSolicitud(pCi int, out retorno int)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    START TRANSACTION;
    SET SQL_SAFE_UPDATES = 0;
    IF NOT EXISTS (SELECT * FROM solicitudes WHERE ci = pCi) THEN
		SET retorno = -1;
	ELSE
		DELETE FROM solicitudes WHERE ci = pCi;
	END IF;
	COMMIT;
END
$$

CREATE PROCEDURE BuscarSolicitud(pCi int)
BEGIN
	SELECT * FROM solicitudes WHERE ci = pCi;
END
$$

-- GENERACIONES
-- ListarGeneraciones - Devuelve la lista de generaciones
CREATE PROCEDURE ListarGeneraciones()
BEGIN
	SELECT * FROM generaciones WHERE genId != 0;
END
$$


DELIMITER ;

CALL AltaGeneracion(0,@retorno);
CALL AltaUsuario(4444444, 0, 'Luis', 'Pérez', '1234', 'luis@gmail.com', 'Admin',@retorno);



CALL AltaGeneracion(2010,@retorno);
CALL AltaGeneracion(2011,@retorno);
CALL AltaGeneracion(2012,@retorno);
CALL AltaGeneracion(2013,@retorno);


CALL AltaUsuario(5555555,2010, 'Juan', 'García', '1234', 'juanxxxxxxx@gmail.com', 'Estudiante',@retorno);
CALL AltaUsuario(7777777,2012, 'Ana', 'Pérez', '1234', 'anaxxxxxxxxx@gmail.com', 'Encargado',@retorno);


CALL AltaSolicitud(4444444, 2012, '2016-10-20', 'José', 'Artigas', '1234', 'jose@hotmail.com', 11111111, @retorno);
CALL AltaSolicitud(3333333, 2012, '2016-10-20', 'Mathias', 'Rodriguez', '1234', 'mathi@hotmail.com', 22222222, @retorno);

CALL VerificarSolicitud(22222222);

CALL AltaReunion(2012,'Aumentar venta de rifas', 'En esta reunión se discutiran alternativas para aumentar la venta de rifas.', '2016-10-20 15:00:00', 120, 1, 'SALON 1', @retorno);
CALL AltaReunion(2010,'Bajar precio de rifas', 'En esta reunión se discutirá el nuevo precio de algunas rifas.', '2016-06-20 15:00:00',60,0, 'SALON 2',@retorno);
CALL AltaReunion(2012,'Aumentar venta de rifas', 'En esta reunión se discutiran alternativas para aumentar la venta de rifas.', '2016-12-20 15:00:00', 30, 0, 'SALON 3', @retorno);
CALL AltaReunion(2012,'Fijación de precios de rifas', 'En esta reunión se discutirá el nuevo precio de algunas rifas.', NOW(),60,1, 'SALON 4',@retorno);

CALL AltaTema(1, 'Disminución de ventas');
CALL AltaTema(1, 'Nuevos premios');
CALL AltaTema(1, 'Fijación de nuevos precios');
CALL AltaTema(4, 'Disminución de ventas');
CALL AltaTema(4, 'Nuevos premios');
CALL AltaTema(4, 'Fijación de nuevos precios');

CALL AltaResolucion(1, 'Se baja el precio de las rifas');
CALL AltaResolucion(1, 'Otra resolucion');

SELECT * FROM asistencias;
SELECT * FROM solicitudes;
SELECT * FROM usuarios;
SELECT * FROM generaciones;
SELECT * FROM reuniones;
SELECT * FROM temas;
SELECT * FROM resoluciones;




use arqrifa;