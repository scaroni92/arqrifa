<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Solicitudes</title>
    </head>
    <body>
        <h1>Listado de Solicitudes</h1>
        <table>
            <tr>
                <th>CÉDULA</th><th>NOMBRE</th><th>APELLIDO</th><th></th><th></th>
            </tr>
            <c:forEach items="${modelo.solicitudes}" var="solicitud">
                <tr>
                    <td>${solicitud.usuario.ci}</td>
                    <td>${solicitud.usuario.nombre}</td>
                    <td>${solicitud.usuario.apellido}</td>
                    <c:choose>
                        <c:when test="${solicitud.verificada}">
                            <td><a href="Encargados?accion=confirmar_solicitud&ci=${solicitud.usuario.ci}">Confirmar</a></td>
                        </c:when>
                        <c:otherwise>
                            <td>Sin verificar</td>
                        </c:otherwise>
                    </c:choose>
                    <td><a href="Encargados?accion=rechazar_solicitud&ci=${solicitud.usuario.ci}">Rechazar</a></td>
                </tr>
            </c:forEach>
        </table>
        <p>Cantidad: ${modelo.cantidad}</p>
        <p>${modelo.mensaje}</p>
    </body>
</html>
