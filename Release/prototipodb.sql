DROP DATABASE IF EXISTS prototipodb;

CREATE DATABASE prototipodb;

USE prototipodb;


CREATE TABLE generaciones(
	genId INT PRIMARY KEY
);

CREATE TABLE estudiantes (
    Ci INT PRIMARY KEY,
    Generacion INT NOT NULL,
    Nombre VARCHAR(20) NOT NULL,
    Apellido VARCHAR(20) NOT NULL,
    Contrasena VARCHAR(20) NOT NULL,
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
    FOREIGN KEY (Ci) REFERENCES estudiantes (Ci),
    PRIMARY KEY (Id , Ci)
);


INSERT INTO generaciones VALUES(2010);
INSERT INTO generaciones VALUES(2011);
INSERT INTO generaciones VALUES(2012);
INSERT INTO generaciones VALUES(2013);
INSERT INTO estudiantes VALUES (5555555,2010, 'Juan', 'García', '1234');
INSERT INTO estudiantes VALUES (7777777,2012, 'Ana', 'Peréz', '1234');
INSERT INTO reuniones (Fecha,Generacion) VALUES ('2016-06-20 15:00:00',2010);

SELECT * FROM asistencias