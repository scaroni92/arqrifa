<%-- 
    Document   : index
    Created on : 13-oct-2016, 0:17:58
    Author     : Ale
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>INICIO</title>
    </head>
    <body>
        <h1>Inicio Administrador</h1>
        <h2>Nombre : ${usuario.nombre} ${usuario.apellido}</h2>
        <h3>Generación: ${usuario.generacion}</h3>
        <a href="Admin?accion=agregar_encargado">Agregar Encargado</a>
        <a href="Admin?accion=agregar_generacion">Agregar Generación</a>
    </body>
</html>
