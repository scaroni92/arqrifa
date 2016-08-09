<%-- 
    Document   : index
    Created on : 26/07/2016, 06:08:41 AM
    Author     : ale
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logueo</title>
        <style>
            h1 {
                text-align: center;
            } 
            form {
                width: 300px;
                margin: auto;
            }
            p {
                color: red;
                text-align: center;
            }
            input[type=number]::-webkit-outer-spin-button,
            input[type=number]::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
        </style>
    </head>
    <body>
        <form action="login" method="POST">
            <table>
                <h1>Iniciar sesión</h1>
                <tr>
                    <td>Usuario:</td>
                    <td><input type="number" name="user" value="5555555" required autofocus="true" /></td>
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
