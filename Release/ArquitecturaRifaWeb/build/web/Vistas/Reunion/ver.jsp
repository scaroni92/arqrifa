<!-- Esta página fue creada provisoriamente para implementar el CU Iniciar Reunión-->

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
        <c:if test="${fn:length(modelo.resoluciones) > 0}">
            Resoluciones:
            <ul>
                <c:forEach var="resolucion" items="${modelo.resoluciones}">
                    <li>${resolucion}</li>
                    </c:forEach>
            </ul>
        </c:if>        
        <c:if test="${!empty modelo.observaciones}" >
            Observaciones: ${modelo.observaciones}
        </c:if>


        <form action="Reuniones" method="post">
            <c:if test="${usuario.rol == 'Encargado' and modelo.habilitarBotonEfectuar}">
                <button type="submit" name="accion" value="efectuar">Efectuar</button>
            </c:if>
            <!-- Estos inputs son para mantener el estado de la reunión-->
            <div style="display:none">
                <input type="text" name="id" value="${modelo.id}" ><br>
                <input type="text" name="titulo" value="${modelo.titulo}"><br>
                <textarea name="descripcion"hidden>${modelo.descripcion}</textarea><br>
                <textarea name="resoluciones" hidden>${modelo.observaciones}</textarea><br>
                <input type="date" name="fecha" value="${modelo.fecha}"><br>
                <input type="time" name="hora" value="${modelo.hora}"><br>
                <input type="checkbox" name="obligatoria" value="${modelo.obligatoria}"><br>
                <input type="text" name="generacion" value="${modelo.generacion}"><br>
                <input type="text" name="estado" value="${modelo.estado}"><br>
                <input type="text" name="lugar" value="${modelo.lugar}"><br> 
            </div>
        </form>

        <p>${modelo.mensaje}</p>
    </body>
</html>
