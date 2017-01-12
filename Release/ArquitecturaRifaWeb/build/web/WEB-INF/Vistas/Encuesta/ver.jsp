<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Detalles de encuesta</title>
    </head>
    <body>
        <h2>Detalles de encuesta</h2>
        <fieldset>
            <h4>${modelo.encuesta.titulo}</h4>
            <p>Estado: <c:choose><c:when test="${modelo.encuesta.habilitada}">Habilitada</c:when><c:otherwise>Inhabilitada</c:otherwise></c:choose></p>
            <p>Duración: ${modelo.encuesta.duracion}</p>
        </fieldset>
        <br>
        <br>
        <fieldset>
            <h4>Propuestas</h4>
            <c:forEach var="propuesta" items="${modelo.encuesta.propuestas}">
                <h5>${propuesta.pregunta}</h5>
                <c:forEach var="respuesta" items="${propuesta.respuestas}">
                    - ${respuesta.respuesta}<br>
                </c:forEach>
            </c:forEach>
        </fieldset>

        <c:if test="${modelo.encuesta.habilitada}">
            <a href="Encuesta?accion=cuestionario&id=${modelo.reunionId}">Agregar voto</a>
        </c:if>

        <c:if test="${!modelo.encuesta.habilitada}">
            <form action="Encuesta" method="post">
                <input type="text" name="reunion_id" value="${modelo.reunionId}" hidden>
                <button type="submit" name="accion" value="iniciar_votacion" >Iniciar votación</button>
            </form>
                <a href="Encuesta?accion=eliminar&id=${modelo.reunionId}">Eliminar</a>
        </c:if>
        ${modelo.mensaje}
    </body>
</html>
