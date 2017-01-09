<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Resumen de Reunion</title>
    </head>
    <body>
        <h1>RESUMEN</h1>
        <h4>${modelo.reunion.titulo}</h4>
        <p>${modelo.reunion.fecha}</p>
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
        <c:if test="${modelo.reunion.estado eq 'Finalizada'}">
            <p>Participantes: ${fn:length(modelo.reunion.participantes)}</p>
        </c:if>

        <a href="Reuniones?accion=ver&id=${modelo.reunion.id}">Ver detalles</a>
    </body>
</html>
