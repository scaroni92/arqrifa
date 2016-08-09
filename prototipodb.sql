DROP DATABASE IF EXISTS prototipodb;

CREATE DATABASE prototipodb;

USE prototipodb;

CREATE TABLE estudiantes (
    Ci INT PRIMARY KEY,
    Nombre VARCHAR(20) NOT NULL,
    Apellido VARCHAR(20) NOT NULL,
    Contrasena VARCHAR(20) NOT NULL
);

CREATE TABLE reuniones (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Fecha DATETIME NOT NULL,
    Obligatoria BIT NOT NULL DEFAULT 0
);

CREATE TABLE asistencias (
    Id INT,
    Ci INT,
    FOREIGN KEY (Id) REFERENCES reuniones (Id),
    FOREIGN KEY (Ci) REFERENCES estudiantes (Ci),
    PRIMARY KEY (Id , Ci)
);

INSERT INTO estudiantes VALUES (5555555, 'Juan', 'García', '1234');
INSERT INTO estudiantes VALUES (7777777, 'Ana', 'Peréz', '1234');
INSERT INTO reuniones (Fecha) VALUES ('2016-06-20 15:00:00');
