<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Arquitectura Rifa | INICIO</title>
    </head>
    <body>
        <h1>Inicio Estudiante</h1>
        <h2>Nombre : ${usuario.nombre} ${usuario.apellido}</h2>
        <h3>Generación: ${usuario.generacion}</h3>
        <br>
        <br>
        <a href="Reuniones?accion=resumen">Resumen de última reunión</a>
        <a href="Reuniones">Ver calendario</a>
    </body>
</html>
