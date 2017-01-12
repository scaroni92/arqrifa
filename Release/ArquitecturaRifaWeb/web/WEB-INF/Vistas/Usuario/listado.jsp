<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de usuarios</h1>
        <table>
            <tr>
                <th>Cédula</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <c:if test="${usuario.rol eq 'Admin'}">
                    <th>Generación</th>
                    <th>Rol</th>
                </c:if>
                <th>Acción</th>
            </tr>
            <c:forEach var="u" items="${modelo.usuarios}">
                <tr>
                    <td>${u.ci}</td>
                    <td>${u.nombre}</td>
                    <td>${u.apellido}</td>
                    <c:if test="${usuario.rol eq 'Admin'}">
                        <td>${u.generacion}</td>
                        <td>${u.rol}</td>
                    </c:if>
                    <td><a href="Usuarios?accion=ver&ci=${u.ci}">Detalles</a></td>
                </tr>
            </c:forEach>
        </table>

        ${modelo.mensaje}
    </body>
</html>
