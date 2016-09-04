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
    Fecha DATETIME NOT NULL,
    Generacion INT NOT NULL,
    Obligatoria BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (Generacion) REFERENCES generaciones(genId)
);

CREATE TABLE asistencias (
    Id INT,
    Ci INT,
    FOREIGN KEY (Id) REFERENCES reuniones (Id),
    FOREIGN KEY (Ci) REFERENCES usuarios (Ci),
    PRIMARY KEY (Id , Ci)
);


INSERT INTO generaciones VALUES(2010),(2011),(2012),(2013);
INSERT INTO usuarios VALUES (5555555,2010, 'Juan', 'García', '1234', 'juan@gmail.com', 'estudiante');
INSERT INTO usuarios VALUES (7777777,2012, 'Ana', 'Peréz', '1234', 'ana@gmail.com', 'encargado');
INSERT INTO reuniones (Fecha,Generacion) VALUES ('2016-06-20 15:00:00',2010);

SELECT * FROM asistencias