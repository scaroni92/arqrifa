<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Resumen de Reunion</title>
    </head>
    <body>
        <h4>${modelo.reunion.titulo}</h4>
        <h1>RESUMEN</h1>
        <h3>Observaciones</h3>
        <p>${modelo.reunion.observaciones}</p>
        <h3>Temas tratados</h3>
        <ul>
            <c:forEach var="tema" items="${modelo.reunion.temas}">
                <li>${tema}</li>
            </c:forEach>
        </ul>
        <h3>Resoluciones alcanzadas</h3>
        <ul>
            <c:forEach var="resolucion" items="${modelo.reunion.resoluciones}">
                <li>${resolucion}</li>
            </c:forEach>
        </ul>

        <p>Participantes: ${modelo.cantidadParticipantes}</p>
    </body>
</html>
