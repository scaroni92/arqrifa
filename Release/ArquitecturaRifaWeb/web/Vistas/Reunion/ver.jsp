<!-- Esta página fue creada provisoriamente para implementar el CU Iniciar Reunión-->

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${modelo.titulo}</h1>
        <h2>${modelo.descripcion}</h2>
        <p>Fecha: ${modelo.fecha}</p>
        <p>Hora: ${modelo.hora}"</p>
        <p>Estado: ${modelo.estado}</p>
        <p>Lugar: ${modelo.lugar}</p>
        <c:if test="${modelo.activa}">
            <form action="Reuniones" method="post">
                <button type="submit" name="accion" value="iniciar_link">Iniciar</button>
                <input type="text" name="id" value="${modelo.id}" hidden>
            </form>
        </c:if> 


        <p>${modelo.mensaje}</p>
    </body>
</html>
