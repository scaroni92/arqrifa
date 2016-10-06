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
        <a href="http://localhost:8080/ArquitecturaRifaWeb/ControladorEncargado?accion=solicitudes">Listar solicitudes</a>
        <p>${modelo.mensaje}</p>
    </body>
</html>
