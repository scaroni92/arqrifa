<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arqutectura Rifa | Agregar voto</title>
    </head>
    <body>
        <p>${encuesta.titulo}</p>
        <form action="Encuesta" method="post">
            <c:if test="${estudiante == null}">
                <input type="number" name="ci" placeholder="Cédula"><input type="submit" name="accion" value="buscar_estudiante">
            </c:if>
        </form>
        <form action="Encuesta" method="post">
            <c:if test="${estudiante != null}">
                <p>Estudiante: ${estudiante.nombre} ${estudiante.apellido}</p>
                <h4>Cuestionario</h4>
                <c:forEach var="propuesta" items="${encuesta.propuestas}">
                    <fieldset>
                        <p>${propuesta.pregunta}</p>
                        <c:forEach var="respuesta" items="${propuesta.respuestas}">
                            <input type="radio" id="${respuesta.id}" name="${propuesta.id}" value="${respuesta.id}"><label for="${respuesta.id}">${respuesta.respuesta}</label><br>
                        </c:forEach>
                    </fieldset>
                    <br>
                </c:forEach>
                <input type="submit" name="accion" value="confirmar_voto">
            </c:if>
        </form>
        <p>${modelo.mensaje}</p>
    </body>
</html>
