<!-- Esta página fue creada provisoriamente para implementar el CU Iniciar Reunión-->

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Detalles de reunión</title>
        <style>
            table td {
                vertical-align: top;
            }
        </style>
    </head>
    <body>
        <h2>Detalles de reunión</h2>
        <fieldset>
            <h4>Estado de reunión: ${modelo.reunion.estado}</h4>
            <p>Fecha y hora de inicio: <fmt:formatDate pattern="dd/MM/yy hh:mm" value="${modelo.reunion.fecha}" /></p>
            <c:choose>
                <c:when test="${modelo.reunion.obligatoria}"><p><u>La asistencia es de carácter obligatorio</u></p></c:when>
                <c:otherwise><p>La asistencia no es de carácter obligatorio</p></c:otherwise>
            </c:choose>
        </fieldset>
        <br>
        <br>
        <fieldset>
            <h4>General</h4>
            <table>
                <tr>
                    <td>ID:</td>
                    <td>${modelo.reunion.id}</td>
                </tr>
                <tr>
                    <td>Título:</td>
                    <td>${modelo.reunion.titulo}</td>
                </tr>
                <tr>
                    <td>Descripción:</td>
                    <td>${modelo.reunion.descripcion}</td>
                </tr>
                <tr>
                    <td>Lugar:</td>
                    <td>${modelo.reunion.lugar}</td>
                </tr>
                <tr>
                    <td>Duración:</td>
                    <td>${modelo.reunion.duracion} minutos</td>
                </tr>
                <tr>
                    <td>Temas:</td>
                    <td>
                        <c:forEach var="tema" items="${modelo.reunion.temas}">
                            - ${tema}<br>
                        </c:forEach>
                    </td>
                </tr>
            </table>
            <c:if test="${modelo.reunion.estado eq 'Finalizada'}">
                <hr>
                <h4>Recapitulación</h4>
                <table>
                    <tr>
                        <td>Participantes:</td>
                        <td>${fn:length(modelo.reunion.participantes)}</td>
                    </tr>
                    <tr>
                        <td>Observaciones:</td>
                        <td>${modelo.reunion.observaciones}</td>
                    </tr>
                    <tr>
                        <td>Resoluciones:</td>
                        <td>
                            <c:forEach var="resolucion" items="${modelo.reunion.resoluciones}">
                                - ${resolucion}<br>
                            </c:forEach>
                        </td>
                    </tr>
                </table>
            </c:if>
        </fieldset>

        <c:if test="${usuario.rol == 'Encargado'}">
            <a href="Reuniones?accion=panel&id=${modelo.reunion.id}">Ir al panel</a>

            <a href="Reuniones?accion=asistencias&id=${modelo.reunion.id}">Ver asistencias</a>

            <c:choose >
                <c:when test="${modelo.reunion.encuesta == null}"><a href="Encuesta?accion=agregar&id=${modelo.reunion.id}">Agregar encuesta</a></c:when>
                <c:otherwise><a href="Encuesta?accion=ver&reunion_id=${modelo.reunion.id}">Ver encuesta</a></c:otherwise>
            </c:choose>
        </c:if>

        <p>${modelo.mensaje}</p>
    </body>
</html>
