<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Login</title>
        <link rel="stylesheet" href="css/estilos.css"/>
    </head>
    <body>
        <h1>Iniciar sesión</h1>
        <form action="Usuarios" method="post">
            <table>
                <tr>
                    <td>Cédula:</td>
                    <td><input type="number" name="ci" value="7777777" autofocus="true" required /></td>
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
