<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Lista de asistencias</h1>
        <table>
            <tr>
                <th>Cédula</th>
                <th>Nombre</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="asistencia" items="${modelo.asistencias}">
                <tr>
                    <td>${asistencia.estudiante.ci}</td>
                    <td>${asistencia.estudiante.nombre} ${asistencia.estudiante.apellido}</td>
                    <td>${asistencia.estado}</td>
                    <td>
                        <c:if test="${(asistencia.estado eq 'Ausente') and (modelo.reunion.estado eq 'Iniciada')}">
                            <a href="#">Marcar asistencia</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
