<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registro</h1>
        <form action="Usuarios" method="post">
            Cédula: <input type="text" name="ci" value="${solicitud.ci}" required autofocus ><br>
            Nombre: <input type="text" name="nombre" value="${solicitud.nombre}" required ><br>
            Apellido: <input type="text" name="apellido" value="${solicitud.apellido}" required ><br>
            Contraseña: <input type="password" name="pass" value="${solicitud.contrasena}" required ><br>
            Email: <input type="email" name="email" value="${solicitud.email}" required ><br>
            Generación: <select name="generacion">
                <c:forEach items="${generaciones}" var="generacion">
                    <option>${generacion.id}</option>
                </c:forEach>
            </select><br>
            <input type="submit" name="accion" value="Registrar">
        </form>
        <p>${modelo.mensaje}</p>
    </body>
</html>
