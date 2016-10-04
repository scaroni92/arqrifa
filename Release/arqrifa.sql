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
    Titulo VARCHAR(30) NOT NULL,
    Descripcion VARCHAR(100) NOT NULL,
    Fecha DATETIME NOT NULL,
    Generacion INT NOT NULL,
    Obligatoria BIT NOT NULL,
    Lugar VARCHAR(50) NOT NULL,
    Resoluciones VARCHAR(100),
    FOREIGN KEY (Generacion) REFERENCES generaciones(genId)
);

CREATE TABLE asistencias (
    Id INT,
    Ci INT,
    FOREIGN KEY (Id) REFERENCES reuniones (Id),
    FOREIGN KEY (Ci) REFERENCES usuarios (Ci),
    PRIMARY KEY (Id , Ci)
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
-- Retorno : -1 si ya existe
CREATE PROCEDURE AltaUsuario(pCi int, pGeneracion int, pNombre varchar(20), pApellido varchar(20), pContrasena varchar(20), pEmail varchar(30), pRol varchar(15),out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM usuarios WHERE ci = pCi) THEN
		SET retorno = -1;
	ELSE
		INSERT INTO usuarios VALUES (pCi,pGeneracion, pNombre, pApellido, pContrasena, pEmail, pRol);
	END IF;
END
$$

-- Autenticar - Busca el usuario con las credenciales dadas
CREATE PROCEDURE Autenticar(pCi int, pContrasena varchar(20))
BEGIN
	SELECT * FROM usuarios WHERE Ci = pCi AND contraseña = pContraseña;
END
$$


-- BuscarEstudiante - Busca el estudiante con las credenciales dadas
CREATE PROCEDURE BuscarEstudiante(pCi int)
BEGIN
	SELECT * FROM usuarios WHERE ci = pCi AND rol = 'estudiante';
END
$$

-- REUNIONES
-- AltaReunion  - Da de alta una reunion
-- Retorno : -1 si existe una reunion para dicha generacion en el mismo dia
CREATE PROCEDURE AltaReunion(pTitulo varchar(30), pDescription varchar(100), pFecha datetime, pgeneracion int, pObligatoria bit, pLugar varchar(50),out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM reuniones WHERE CAST(Fecha as date) = CAST(pFecha as date)) THEN
		SET retorno = -1;
	ELSE
		INSERT INTO reuniones (Titulo,Descripcion,Fecha,Generacion,Obligatoria,Lugar) VALUES (pTitulo, pDescription, pFecha,pgeneracion,pObligatoria, pLugar);
	END IF;
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

-- ASISTENCIAS
-- MarcarAsistencia - Marca la asistencia de un estudiante a una reunion
-- Retorno : -1 si ya tiene asistencia, -2 si la reunion no existe , -3 si el estudiante no existe
CREATE PROCEDURE MarcarAsistencia(pId int, pCi int,out retorno int)
BEGIN
	IF EXISTS (SELECT * FROM asistencias WHERE Id = pId and Ci = pCi) THEN
		SET retorno = -1;
	ELSEIF NOT EXISTS (SELECT * FROM reuniones WHERE Id = pId) THEN
		SET retorno = -2;
	ELSEIF NOT EXISTS (SELECT * FROM usuarios WHERE Ci = pCi) THEN
		SET retorno = -3;
	ELSE
		INSERT INTO asistencias VALUES (pId,pCi);
	END IF;
END
$$

DELIMITER ;


CALL AltaGeneracion(2010,@retorno);
CALL AltaGeneracion(2011,@retorno);
CALL AltaGeneracion(2012,@retorno);
CALL AltaGeneracion(2013,@retorno);

CALL AltaUsuario(5555555,2010, 'Juan', 'García', '1234', 'juan@gmail.com', 'estudiante',@retorno);
CALL AltaUsuario(7777777,2012, 'Ana', 'Peréz', '1234', 'ana@gmail.com', 'encargado',@retorno);
CALL AltaReunion('titulo', 'desc', '2016-06-20 15:00:00',2010,0, 'lugar',@retorno);
-- CALL MarcarAsistencia(1,5555555,@retorno);
SELECT * FROM asistencias

