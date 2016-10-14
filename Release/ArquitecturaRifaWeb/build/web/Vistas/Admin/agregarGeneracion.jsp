<%-- 
    Document   : agregarGeneracion
    Created on : 13-oct-2016, 20:37:44
    Author     : Ale
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Agregar Generación</h1>
        <form action="Admin" method="post">
            Año: <input type="text" name="anio">
            <button type="submit" name="accion" value="agregar_generacion">Agregar</button>
        </form>
        <p>${modelo.mensaje}</p>
    </body>
</html>
