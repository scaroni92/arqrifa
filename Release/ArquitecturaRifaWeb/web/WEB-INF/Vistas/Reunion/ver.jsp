<!-- Esta página fue creada provisoriamente para implementar el CU Iniciar Reunión-->

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${modelo.reunion.titulo}</h1>
        <h2>${modelo.reunion.descripcion}</h2>
        Temas:
        <ul>
            <c:forEach var="tema" items="${modelo.reunion.temas}">
                <li>${tema}</li>
                </c:forEach>
        </ul>
        Fecha:<fmt:formatDate pattern="dd/MM/yy" value="${modelo.reunion.fecha}" /><br>
        Hora: <fmt:formatDate pattern="hh:mm" value="${modelo.reunion.fecha}" /><br>
        Obligatoria: <c:choose><c:when test="${modelo.reunion.obligatoria}">Si</c:when><c:otherwise>No</c:otherwise></c:choose><br>
        Estado: ${modelo.reunion.estado}<br>
        Lugar: ${modelo.reunion.lugar}<br><br>
        <c:if test="${modelo.reunion.estado eq 'Finalizada'}">
            Resoluciones:
            <ul>
                <c:forEach var="resolucion" items="${modelo.reunion.resoluciones}">
                    <li>${resolucion}</li>
                    </c:forEach>
            </ul>
            Observaciones: ${modelo.reunion.observaciones}
        </c:if>


        <c:if test="${usuario.rol == 'Encargado'}">
            <form action="Reuniones" method="post">
                <button type="submit" name="accion" value="panel">Ir al panel</button>
                <input type="text" name="id" value="${modelo.reunion.id}" hidden/>
            </form>
        </c:if>

        <a href="Encuesta?accion=ver&reunion_id=${modelo.reunion.id}">Ver encuesta</a>
        <a href="Encuesta?accion=agregar&id=${modelo.reunion.id}">Agregar encuesta</a>
        <p>${modelo.mensaje}</p>
    </body>
</html>
