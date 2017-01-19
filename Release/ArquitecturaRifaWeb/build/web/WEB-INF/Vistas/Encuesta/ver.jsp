<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Detalles de encuesta</title>
    </head>
    <body>
        <c:set scope="page" value="${modelo.reunion.encuesta}" var="encuesta" />
        <h2>Detalles de encuesta</h2>
        <fieldset>
            <h4>${encuesta.titulo}</h4>
            <p>Estado: <c:choose><c:when test="${encuesta.habilitada}">Habilitada</c:when><c:otherwise>Inhabilitada</c:otherwise></c:choose></p>
            <p>Duración: ${encuesta.duracion}</p>
        </fieldset>
        <br>
        <br>
        <fieldset>
            <h4>Propuestas</h4>
            <c:forEach var="propuesta" items="${encuesta.propuestas}">
                <h5>${propuesta.pregunta}</h5>
                <table>
                    <tr>
                        <th>Respuestas</th>
                        <th>Votos</th>
                    </tr>
                    <c:forEach var="respuesta" items="${propuesta.respuestas}">
                        <tr>
                            <td>${respuesta.respuesta}</td>
                            <td>${respuesta.cantidadVotos}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:forEach>
        </fieldset>

        <c:if test="${encuesta.habilitada}">
            <a href="Encuesta?accion=cuestionario&reunion_id=${modelo.reunion.id}">Agregar voto</a>
        </c:if>

        <c:if test="${!encuesta.habilitada}">
            <form action="Encuesta" method="post">
                <input type="hidden" name="reunion_id" value="${modelo.reunion.id}">
                <button type="submit" name="accion" value="iniciar_votacion" >Iniciar votación</button>
            </form>
            <a href="Encuesta?accion=eliminar&id=${encuesta.id}">Eliminar</a>
            <a href="Encuesta?accion=modificar&id=${encuesta.id}">Modificar</a>
        </c:if>

        ${modelo.mensaje}
    </body>
</html>
