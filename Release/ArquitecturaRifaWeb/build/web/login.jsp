<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Arquitectura Rifa | Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/estilos.css"/>
    </head>
    <body>
        <form action="Usuarios" method="post">
            <table>
                <h1>Iniciar sesión</h1>
                <tr>
                    <td>Usuario:</td>
                    <td><input type="number" name="user" value="7777777" autofocus="true" required /></td>
                </tr>
                <tr>
                    <td>Contraseña:</td>
                    <td><input type="password" name="pass" value="1234" required /></td>
                </tr>
                <tr>
                    <td colspan="2" align="right"><input type="submit" name="accion" value="Login" /></td>
                </tr>
            </table>
            <p>${modelo.mensaje}</p>
        </form>
        <a href="Usuarios?accion=registrar">Registrarse</a>
    </body>
</html>
