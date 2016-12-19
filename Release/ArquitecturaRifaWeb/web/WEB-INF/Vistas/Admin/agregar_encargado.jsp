<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Agregar Encargado</title>
    </head>
    <body>
        <h1>Agregar Encargado</h1>
        <form action="Admin" method="post">
            Cédula: <input type="number" name="ci" value="${modelo.ci}" required autofocus><br>
            Nombre: <input type="text" name="nombre" value="${modelo.nombre}" required><br>
            Apellido: <input type="text" name="apellido" value="${modelo.apellido}" required><br>
            Contraseña: <input type="password" name="contrasena" value="${modelo.contrasena}" required><br>
            Email: <input type="email" name="email" value="${modelo.email}" pattern="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*" title="ejemplo@ejemplo.com" required><br>
            Generación:
            <select name="generacion">
                <c:forEach items="${modelo.generaciones}" var="generacion">
                    <option selected>${generacion.id}</option>
                </c:forEach>
            </select><br>
            <button type="submit" name="accion" value="agregar_encargado">Agregar</button>
        </form>
        <p>${modelo.mensaje}</p>
    </body>
</html>
