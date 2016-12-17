<!-- Esta página fue creada provisoriamente para implementar el CU Iniciar Reunión-->

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${modelo.titulo}</h1>
        <h2>${modelo.descripcion}</h2>
        Temas:
        <ul>
            <c:forEach var="tema" items="${modelo.temas}">
                <li>${tema}</li>
                </c:forEach>
        </ul>
        Fecha: ${modelo.fecha}<br>
        Hora: ${modelo.hora}<br>
        Obligatoria: <c:choose><c:when test="${modelo.obligatoria}">Si</c:when><c:otherwise>No</c:otherwise></c:choose><br>
        Estado: ${modelo.estado}<br>
        Lugar: ${modelo.lugar}<br><br>
        <c:if test="${modelo.estado eq 'Finalizada'}">
            Resoluciones:
            <ul>
                <c:forEach var="resolucion" items="${modelo.resoluciones}">
                    <li>${resolucion}</li>
                </c:forEach>
            </ul>
            Observaciones: ${modelo.observaciones}
        </c:if>

        <form action="Reuniones" method="post">
            <c:if test="${usuario.rol == 'Encargado' and modelo.habilitarBotonPanel}">
                La reunión está lista para ser iniciada <button type="submit" name="accion" value="panel">Ir al panel</button>
                <input type="text" name="id" value="${modelo.id}" hidden/>
            </c:if>
        </form>

            <c:if test="${!modelo.estado eq 'Finalizada'}">
                
            </c:if>
        <a href="Encuesta?accion=agregar&id=${modelo.id}">Agregar encuesta</a>
        <p>${modelo.mensaje}</p>
    </body>
</html>
