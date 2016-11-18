<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Inicio Encargado</h1>
        <h2>Nombre : ${usuario.nombre} ${usuario.apellido}</h2>
        <h3>Generación: ${usuario.generacion}</h3>
        <a href="Encargados?accion=solicitudes">Listar solicitudes</a>
        <a href="Encargados?accion=agendar">Agendar reunion</a>
        <p>${modelo.mensaje}</p>
        <br>
        <br>
        <a href="Reuniones?accion=ver&id=1">Ver reunión</a>
    </body>
</html>
