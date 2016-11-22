<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de Solicitudes</h1>
        <h2>Generación ${usuario.generacion}</h2>
        <table>
            <tr>
                <th>CÉDULA</th><th>NOMBRE</th><th>APELLIDO</th><th></th><th></th>
            </tr>

            <c:forEach items="${modelo.solicitudes}" var="solicitud">
                <tr>
                    <td style="text-align: center;">${solicitud.usuario.ci}</td>
                    <td>${solicitud.usuario.nombre}</td>
                    <td>${solicitud.usuario.apellido}</td>
                    <c:choose>
                        <c:when test="${solicitud.verificada}">
                            <td><a href="Encargados?accion=confirmar&ci=${solicitud.usuario.ci}">Confirmar</a></td>
                        </c:when>
                        <c:otherwise>
                            <td><span>Correo no verificado</span></td>
                        </c:otherwise>
                    </c:choose>
                    <td><a href="Encargados?accion=rechazar&ci=${solicitud.usuario.ci}">Rechazar</a></td>
                </tr>
            </c:forEach>
        </table>
        <p>Cantidad: ${modelo.cantidad}</p>
        <p>${modelo.mensaje}</p>
    </body>
</html>
