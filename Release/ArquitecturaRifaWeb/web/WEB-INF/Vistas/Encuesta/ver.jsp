<%-- 
    Document   : ver
    Created on : 19-dic-2016, 8:23:04
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
        <h1>Hello World!</h1>
        ${modelo.encuesta.titulo}
        <form action="Encuesta" method="post">
            <input type="text" name="reunion_id" value="${modelo.reunionId}">
            <button type="submit" name="accion" value="iniciar_votacion" >Iniciar votación</button>
        </form>
            ${modelo.mensaje}
    </body>
</html>
