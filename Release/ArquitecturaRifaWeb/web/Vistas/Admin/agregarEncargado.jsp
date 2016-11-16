<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Agregar Encargado</h1>
        <form action="Admin" method="post">
            Cédula: <input type="text" name="ci" value="${modelo.ci}" autofocus ><br>
            Nombre: <input type="text" name="nombre" value="${modelo.nombre}" ><br>
            Apellido: <input type="text" name="apellido" value="${modelo.apellido}" ><br>
            Contraseña: <input type="password" name="contrasena" value="${modelo.contrasena}" ><br>
            Email: <input type="email" name="email" value="${modelo.email}" ><br>
            
            Generación: <select name="generacion">
                <c:forEach items="${modelo.generaciones}" var="generacion">
                    <option selected>${generacion.id}</option>
                </c:forEach>
            </select><br>
            <button type="submit" name="accion" value="agregar_encargado">Agregar</button>
        </form>
        <p>${modelo.mensaje}</p>
    </body>
</html>
