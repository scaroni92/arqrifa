<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/estilos.css"/>
    </head>
    <body>
        <form action="login" method="POST">
            <table>
                <h1>Iniciar sesión</h1>
                <tr>
                    <td>Usuario:</td>
                    <td><input type="number" name="user" value="5555555" autofocus="true" required /></td>
                </tr>
                <tr>
                    <td>Contraseña:</td>
                    <td><input type="password" name="pass" value="1234" required /></td>
                </tr>
                <tr>
                    <td colspan="2" align="right"><input type="submit" value="Autenticar" name="autenticar" /></td>
                </tr>
            </table>
            <p>${mensaje}</p>
        </form>
    </body>
</html>
