<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Detalles estudiante</title>
    </head>
    <body>
        <h3>Detalles de usuario</h3>
        <table>
            <tr>
                <td>Cédula</td>
                <td>${modelo.usuario.ci}</td>
            </tr>
            <tr>
                <td>Nombre</td>
                <td>${modelo.usuario.nombre}</td>
            </tr>
            <tr>
                <td>Apellido</td>
                <td>${modelo.usuario.apellido}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${modelo.usuario.email}</td>
            </tr>
            <c:if test="${usuario.rol eq 'Admin'}">
                <tr>
                    <td>Contraseña</td>
                    <td>${modelo.usuario.contrasena}</td>
                </tr>
            </c:if>
            <c:if test="${modelo.usuario.rol eq 'Estudiante'}">
                <tr>
                    <td>Inasistencias</td>
                    <td>${modelo.inasistencias}</td>
                </tr>                         
            </c:if>
            <tr>
                <td>Rol</td>
                <td>${modelo.usuario.rol}</td>
            </tr>
        </table>
        ${modelo.mensaje}
    </body>
</html>
